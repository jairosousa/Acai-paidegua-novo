package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.HorarioFuncionamento;
import br.com.acaipaidegua.repository.HorarioFuncionamentoRepository;
import br.com.acaipaidegua.service.HorarioFuncionamentoService;
import br.com.acaipaidegua.service.dto.HorarioFuncionamentoDTO;
import br.com.acaipaidegua.service.mapper.HorarioFuncionamentoMapper;
import br.com.acaipaidegua.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static br.com.acaipaidegua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.acaipaidegua.domain.enumeration.TipoHorario;
/**
 * Test class for the HorarioFuncionamentoResource REST controller.
 *
 * @see HorarioFuncionamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class HorarioFuncionamentoResourceIntTest {

    private static final TipoHorario DEFAULT_TIPO = TipoHorario.COMERCIAL;
    private static final TipoHorario UPDATED_TIPO = TipoHorario.DELIVERY;

    private static final String DEFAULT_DIAS_SEMANA = "AAAAAAAAAA";
    private static final String UPDATED_DIAS_SEMANA = "BBBBBBBBBB";

    private static final String DEFAULT_HR_ABERTURA = "AAAAAAAAAA";
    private static final String UPDATED_HR_ABERTURA = "BBBBBBBBBB";

    private static final String DEFAULT_HR_FECHAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_HR_FECHAMENTO = "BBBBBBBBBB";

    @Autowired
    private HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    @Autowired
    private HorarioFuncionamentoMapper horarioFuncionamentoMapper;

    @Autowired
    private HorarioFuncionamentoService horarioFuncionamentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHorarioFuncionamentoMockMvc;

    private HorarioFuncionamento horarioFuncionamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorarioFuncionamentoResource horarioFuncionamentoResource = new HorarioFuncionamentoResource(horarioFuncionamentoService);
        this.restHorarioFuncionamentoMockMvc = MockMvcBuilders.standaloneSetup(horarioFuncionamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioFuncionamento createEntity(EntityManager em) {
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento()
            .tipo(DEFAULT_TIPO)
            .diasSemana(DEFAULT_DIAS_SEMANA)
            .hrAbertura(DEFAULT_HR_ABERTURA)
            .hrFechamento(DEFAULT_HR_FECHAMENTO);
        return horarioFuncionamento;
    }

    @Before
    public void initTest() {
        horarioFuncionamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioFuncionamento() throws Exception {
        int databaseSizeBeforeCreate = horarioFuncionamentoRepository.findAll().size();

        // Create the HorarioFuncionamento
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);
        restHorarioFuncionamentoMockMvc.perform(post("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the HorarioFuncionamento in the database
        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioFuncionamento testHorarioFuncionamento = horarioFuncionamentoList.get(horarioFuncionamentoList.size() - 1);
        assertThat(testHorarioFuncionamento.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testHorarioFuncionamento.getDiasSemana()).isEqualTo(DEFAULT_DIAS_SEMANA);
        assertThat(testHorarioFuncionamento.getHrAbertura()).isEqualTo(DEFAULT_HR_ABERTURA);
        assertThat(testHorarioFuncionamento.getHrFechamento()).isEqualTo(DEFAULT_HR_FECHAMENTO);
    }

    @Test
    @Transactional
    public void createHorarioFuncionamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioFuncionamentoRepository.findAll().size();

        // Create the HorarioFuncionamento with an existing ID
        horarioFuncionamento.setId(1L);
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioFuncionamentoMockMvc.perform(post("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioFuncionamento in the database
        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioFuncionamentoRepository.findAll().size();
        // set the field null
        horarioFuncionamento.setTipo(null);

        // Create the HorarioFuncionamento, which fails.
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);

        restHorarioFuncionamentoMockMvc.perform(post("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiasSemanaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioFuncionamentoRepository.findAll().size();
        // set the field null
        horarioFuncionamento.setDiasSemana(null);

        // Create the HorarioFuncionamento, which fails.
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);

        restHorarioFuncionamentoMockMvc.perform(post("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHrAberturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioFuncionamentoRepository.findAll().size();
        // set the field null
        horarioFuncionamento.setHrAbertura(null);

        // Create the HorarioFuncionamento, which fails.
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);

        restHorarioFuncionamentoMockMvc.perform(post("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHrFechamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioFuncionamentoRepository.findAll().size();
        // set the field null
        horarioFuncionamento.setHrFechamento(null);

        // Create the HorarioFuncionamento, which fails.
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);

        restHorarioFuncionamentoMockMvc.perform(post("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarioFuncionamentos() throws Exception {
        // Initialize the database
        horarioFuncionamentoRepository.saveAndFlush(horarioFuncionamento);

        // Get all the horarioFuncionamentoList
        restHorarioFuncionamentoMockMvc.perform(get("/api/horario-funcionamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioFuncionamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].diasSemana").value(hasItem(DEFAULT_DIAS_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].hrAbertura").value(hasItem(DEFAULT_HR_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].hrFechamento").value(hasItem(DEFAULT_HR_FECHAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getHorarioFuncionamento() throws Exception {
        // Initialize the database
        horarioFuncionamentoRepository.saveAndFlush(horarioFuncionamento);

        // Get the horarioFuncionamento
        restHorarioFuncionamentoMockMvc.perform(get("/api/horario-funcionamentos/{id}", horarioFuncionamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horarioFuncionamento.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.diasSemana").value(DEFAULT_DIAS_SEMANA.toString()))
            .andExpect(jsonPath("$.hrAbertura").value(DEFAULT_HR_ABERTURA.toString()))
            .andExpect(jsonPath("$.hrFechamento").value(DEFAULT_HR_FECHAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHorarioFuncionamento() throws Exception {
        // Get the horarioFuncionamento
        restHorarioFuncionamentoMockMvc.perform(get("/api/horario-funcionamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioFuncionamento() throws Exception {
        // Initialize the database
        horarioFuncionamentoRepository.saveAndFlush(horarioFuncionamento);

        int databaseSizeBeforeUpdate = horarioFuncionamentoRepository.findAll().size();

        // Update the horarioFuncionamento
        HorarioFuncionamento updatedHorarioFuncionamento = horarioFuncionamentoRepository.findById(horarioFuncionamento.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioFuncionamento are not directly saved in db
        em.detach(updatedHorarioFuncionamento);
        updatedHorarioFuncionamento
            .tipo(UPDATED_TIPO)
            .diasSemana(UPDATED_DIAS_SEMANA)
            .hrAbertura(UPDATED_HR_ABERTURA)
            .hrFechamento(UPDATED_HR_FECHAMENTO);
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(updatedHorarioFuncionamento);

        restHorarioFuncionamentoMockMvc.perform(put("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isOk());

        // Validate the HorarioFuncionamento in the database
        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeUpdate);
        HorarioFuncionamento testHorarioFuncionamento = horarioFuncionamentoList.get(horarioFuncionamentoList.size() - 1);
        assertThat(testHorarioFuncionamento.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testHorarioFuncionamento.getDiasSemana()).isEqualTo(UPDATED_DIAS_SEMANA);
        assertThat(testHorarioFuncionamento.getHrAbertura()).isEqualTo(UPDATED_HR_ABERTURA);
        assertThat(testHorarioFuncionamento.getHrFechamento()).isEqualTo(UPDATED_HR_FECHAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioFuncionamento() throws Exception {
        int databaseSizeBeforeUpdate = horarioFuncionamentoRepository.findAll().size();

        // Create the HorarioFuncionamento
        HorarioFuncionamentoDTO horarioFuncionamentoDTO = horarioFuncionamentoMapper.toDto(horarioFuncionamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioFuncionamentoMockMvc.perform(put("/api/horario-funcionamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioFuncionamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioFuncionamento in the database
        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHorarioFuncionamento() throws Exception {
        // Initialize the database
        horarioFuncionamentoRepository.saveAndFlush(horarioFuncionamento);

        int databaseSizeBeforeDelete = horarioFuncionamentoRepository.findAll().size();

        // Delete the horarioFuncionamento
        restHorarioFuncionamentoMockMvc.perform(delete("/api/horario-funcionamentos/{id}", horarioFuncionamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HorarioFuncionamento> horarioFuncionamentoList = horarioFuncionamentoRepository.findAll();
        assertThat(horarioFuncionamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioFuncionamento.class);
        HorarioFuncionamento horarioFuncionamento1 = new HorarioFuncionamento();
        horarioFuncionamento1.setId(1L);
        HorarioFuncionamento horarioFuncionamento2 = new HorarioFuncionamento();
        horarioFuncionamento2.setId(horarioFuncionamento1.getId());
        assertThat(horarioFuncionamento1).isEqualTo(horarioFuncionamento2);
        horarioFuncionamento2.setId(2L);
        assertThat(horarioFuncionamento1).isNotEqualTo(horarioFuncionamento2);
        horarioFuncionamento1.setId(null);
        assertThat(horarioFuncionamento1).isNotEqualTo(horarioFuncionamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioFuncionamentoDTO.class);
        HorarioFuncionamentoDTO horarioFuncionamentoDTO1 = new HorarioFuncionamentoDTO();
        horarioFuncionamentoDTO1.setId(1L);
        HorarioFuncionamentoDTO horarioFuncionamentoDTO2 = new HorarioFuncionamentoDTO();
        assertThat(horarioFuncionamentoDTO1).isNotEqualTo(horarioFuncionamentoDTO2);
        horarioFuncionamentoDTO2.setId(horarioFuncionamentoDTO1.getId());
        assertThat(horarioFuncionamentoDTO1).isEqualTo(horarioFuncionamentoDTO2);
        horarioFuncionamentoDTO2.setId(2L);
        assertThat(horarioFuncionamentoDTO1).isNotEqualTo(horarioFuncionamentoDTO2);
        horarioFuncionamentoDTO1.setId(null);
        assertThat(horarioFuncionamentoDTO1).isNotEqualTo(horarioFuncionamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(horarioFuncionamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(horarioFuncionamentoMapper.fromId(null)).isNull();
    }
}
