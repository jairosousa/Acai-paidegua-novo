package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.Residuo;
import br.com.acaipaidegua.repository.ResiduoRepository;
import br.com.acaipaidegua.service.ResiduoService;
import br.com.acaipaidegua.service.dto.ResiduoDTO;
import br.com.acaipaidegua.service.mapper.ResiduoMapper;
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
import java.math.BigDecimal;
import java.util.List;


import static br.com.acaipaidegua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.acaipaidegua.domain.enumeration.Destino;
/**
 * Test class for the ResiduoResource REST controller.
 *
 * @see ResiduoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class ResiduoResourceIntTest {

    private static final Destino DEFAULT_DESTINO = Destino.LIXEIRO;
    private static final Destino UPDATED_DESTINO = Destino.MEIO_AMBIENTE;

    private static final String DEFAULT_OUTRO = "AAAAAAAAAA";
    private static final String UPDATED_OUTRO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CUSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTO = new BigDecimal(2);

    private static final String DEFAULT_RESPONSAVEL = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSAVEL = "BBBBBBBBBB";

    @Autowired
    private ResiduoRepository residuoRepository;

    @Autowired
    private ResiduoMapper residuoMapper;

    @Autowired
    private ResiduoService residuoService;

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

    private MockMvc restResiduoMockMvc;

    private Residuo residuo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResiduoResource residuoResource = new ResiduoResource(residuoService);
        this.restResiduoMockMvc = MockMvcBuilders.standaloneSetup(residuoResource)
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
    public static Residuo createEntity(EntityManager em) {
        Residuo residuo = new Residuo()
            .destino(DEFAULT_DESTINO)
            .outro(DEFAULT_OUTRO)
            .custo(DEFAULT_CUSTO)
            .responsavel(DEFAULT_RESPONSAVEL);
        return residuo;
    }

    @Before
    public void initTest() {
        residuo = createEntity(em);
    }

    @Test
    @Transactional
    public void createResiduo() throws Exception {
        int databaseSizeBeforeCreate = residuoRepository.findAll().size();

        // Create the Residuo
        ResiduoDTO residuoDTO = residuoMapper.toDto(residuo);
        restResiduoMockMvc.perform(post("/api/residuos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residuoDTO)))
            .andExpect(status().isCreated());

        // Validate the Residuo in the database
        List<Residuo> residuoList = residuoRepository.findAll();
        assertThat(residuoList).hasSize(databaseSizeBeforeCreate + 1);
        Residuo testResiduo = residuoList.get(residuoList.size() - 1);
        assertThat(testResiduo.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testResiduo.getOutro()).isEqualTo(DEFAULT_OUTRO);
        assertThat(testResiduo.getCusto()).isEqualTo(DEFAULT_CUSTO);
        assertThat(testResiduo.getResponsavel()).isEqualTo(DEFAULT_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void createResiduoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = residuoRepository.findAll().size();

        // Create the Residuo with an existing ID
        residuo.setId(1L);
        ResiduoDTO residuoDTO = residuoMapper.toDto(residuo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResiduoMockMvc.perform(post("/api/residuos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residuoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Residuo in the database
        List<Residuo> residuoList = residuoRepository.findAll();
        assertThat(residuoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDestinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = residuoRepository.findAll().size();
        // set the field null
        residuo.setDestino(null);

        // Create the Residuo, which fails.
        ResiduoDTO residuoDTO = residuoMapper.toDto(residuo);

        restResiduoMockMvc.perform(post("/api/residuos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residuoDTO)))
            .andExpect(status().isBadRequest());

        List<Residuo> residuoList = residuoRepository.findAll();
        assertThat(residuoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResiduos() throws Exception {
        // Initialize the database
        residuoRepository.saveAndFlush(residuo);

        // Get all the residuoList
        restResiduoMockMvc.perform(get("/api/residuos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(residuo.getId().intValue())))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO.toString())))
            .andExpect(jsonPath("$.[*].outro").value(hasItem(DEFAULT_OUTRO.toString())))
            .andExpect(jsonPath("$.[*].custo").value(hasItem(DEFAULT_CUSTO.intValue())))
            .andExpect(jsonPath("$.[*].responsavel").value(hasItem(DEFAULT_RESPONSAVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getResiduo() throws Exception {
        // Initialize the database
        residuoRepository.saveAndFlush(residuo);

        // Get the residuo
        restResiduoMockMvc.perform(get("/api/residuos/{id}", residuo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(residuo.getId().intValue()))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO.toString()))
            .andExpect(jsonPath("$.outro").value(DEFAULT_OUTRO.toString()))
            .andExpect(jsonPath("$.custo").value(DEFAULT_CUSTO.intValue()))
            .andExpect(jsonPath("$.responsavel").value(DEFAULT_RESPONSAVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResiduo() throws Exception {
        // Get the residuo
        restResiduoMockMvc.perform(get("/api/residuos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResiduo() throws Exception {
        // Initialize the database
        residuoRepository.saveAndFlush(residuo);

        int databaseSizeBeforeUpdate = residuoRepository.findAll().size();

        // Update the residuo
        Residuo updatedResiduo = residuoRepository.findById(residuo.getId()).get();
        // Disconnect from session so that the updates on updatedResiduo are not directly saved in db
        em.detach(updatedResiduo);
        updatedResiduo
            .destino(UPDATED_DESTINO)
            .outro(UPDATED_OUTRO)
            .custo(UPDATED_CUSTO)
            .responsavel(UPDATED_RESPONSAVEL);
        ResiduoDTO residuoDTO = residuoMapper.toDto(updatedResiduo);

        restResiduoMockMvc.perform(put("/api/residuos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residuoDTO)))
            .andExpect(status().isOk());

        // Validate the Residuo in the database
        List<Residuo> residuoList = residuoRepository.findAll();
        assertThat(residuoList).hasSize(databaseSizeBeforeUpdate);
        Residuo testResiduo = residuoList.get(residuoList.size() - 1);
        assertThat(testResiduo.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testResiduo.getOutro()).isEqualTo(UPDATED_OUTRO);
        assertThat(testResiduo.getCusto()).isEqualTo(UPDATED_CUSTO);
        assertThat(testResiduo.getResponsavel()).isEqualTo(UPDATED_RESPONSAVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingResiduo() throws Exception {
        int databaseSizeBeforeUpdate = residuoRepository.findAll().size();

        // Create the Residuo
        ResiduoDTO residuoDTO = residuoMapper.toDto(residuo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResiduoMockMvc.perform(put("/api/residuos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(residuoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Residuo in the database
        List<Residuo> residuoList = residuoRepository.findAll();
        assertThat(residuoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResiduo() throws Exception {
        // Initialize the database
        residuoRepository.saveAndFlush(residuo);

        int databaseSizeBeforeDelete = residuoRepository.findAll().size();

        // Delete the residuo
        restResiduoMockMvc.perform(delete("/api/residuos/{id}", residuo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Residuo> residuoList = residuoRepository.findAll();
        assertThat(residuoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Residuo.class);
        Residuo residuo1 = new Residuo();
        residuo1.setId(1L);
        Residuo residuo2 = new Residuo();
        residuo2.setId(residuo1.getId());
        assertThat(residuo1).isEqualTo(residuo2);
        residuo2.setId(2L);
        assertThat(residuo1).isNotEqualTo(residuo2);
        residuo1.setId(null);
        assertThat(residuo1).isNotEqualTo(residuo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResiduoDTO.class);
        ResiduoDTO residuoDTO1 = new ResiduoDTO();
        residuoDTO1.setId(1L);
        ResiduoDTO residuoDTO2 = new ResiduoDTO();
        assertThat(residuoDTO1).isNotEqualTo(residuoDTO2);
        residuoDTO2.setId(residuoDTO1.getId());
        assertThat(residuoDTO1).isEqualTo(residuoDTO2);
        residuoDTO2.setId(2L);
        assertThat(residuoDTO1).isNotEqualTo(residuoDTO2);
        residuoDTO1.setId(null);
        assertThat(residuoDTO1).isNotEqualTo(residuoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(residuoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(residuoMapper.fromId(null)).isNull();
    }
}
