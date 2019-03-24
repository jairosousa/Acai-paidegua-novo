package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.Beneficiamento;
import br.com.acaipaidegua.repository.BeneficiamentoRepository;
import br.com.acaipaidegua.service.BeneficiamentoService;
import br.com.acaipaidegua.service.dto.BeneficiamentoDTO;
import br.com.acaipaidegua.service.mapper.BeneficiamentoMapper;
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

import br.com.acaipaidegua.domain.enumeration.Periodo;
import br.com.acaipaidegua.domain.enumeration.Unidade;
/**
 * Test class for the BeneficiamentoResource REST controller.
 *
 * @see BeneficiamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class BeneficiamentoResourceIntTest {

    private static final Periodo DEFAULT_PERIODO = Periodo.SAFRA;
    private static final Periodo UPDATED_PERIODO = Periodo.ENTRE_SAFRA;

    private static final BigDecimal DEFAULT_QUANTIDADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTIDADE = new BigDecimal(2);

    private static final Unidade DEFAULT_UNIDADE = Unidade.LATA;
    private static final Unidade UPDATED_UNIDADE = Unidade.SACA;

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Autowired
    private BeneficiamentoRepository beneficiamentoRepository;

    @Autowired
    private BeneficiamentoMapper beneficiamentoMapper;

    @Autowired
    private BeneficiamentoService beneficiamentoService;

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

    private MockMvc restBeneficiamentoMockMvc;

    private Beneficiamento beneficiamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficiamentoResource beneficiamentoResource = new BeneficiamentoResource(beneficiamentoService);
        this.restBeneficiamentoMockMvc = MockMvcBuilders.standaloneSetup(beneficiamentoResource)
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
    public static Beneficiamento createEntity(EntityManager em) {
        Beneficiamento beneficiamento = new Beneficiamento()
            .periodo(DEFAULT_PERIODO)
            .quantidade(DEFAULT_QUANTIDADE)
            .unidade(DEFAULT_UNIDADE)
            .total(DEFAULT_TOTAL);
        return beneficiamento;
    }

    @Before
    public void initTest() {
        beneficiamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiamento() throws Exception {
        int databaseSizeBeforeCreate = beneficiamentoRepository.findAll().size();

        // Create the Beneficiamento
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(beneficiamento);
        restBeneficiamentoMockMvc.perform(post("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Beneficiamento in the database
        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiamento testBeneficiamento = beneficiamentoList.get(beneficiamentoList.size() - 1);
        assertThat(testBeneficiamento.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testBeneficiamento.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testBeneficiamento.getUnidade()).isEqualTo(DEFAULT_UNIDADE);
        assertThat(testBeneficiamento.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createBeneficiamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiamentoRepository.findAll().size();

        // Create the Beneficiamento with an existing ID
        beneficiamento.setId(1L);
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(beneficiamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiamentoMockMvc.perform(post("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiamento in the database
        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPeriodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiamentoRepository.findAll().size();
        // set the field null
        beneficiamento.setPeriodo(null);

        // Create the Beneficiamento, which fails.
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(beneficiamento);

        restBeneficiamentoMockMvc.perform(post("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiamentoRepository.findAll().size();
        // set the field null
        beneficiamento.setQuantidade(null);

        // Create the Beneficiamento, which fails.
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(beneficiamento);

        restBeneficiamentoMockMvc.perform(post("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiamentoRepository.findAll().size();
        // set the field null
        beneficiamento.setUnidade(null);

        // Create the Beneficiamento, which fails.
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(beneficiamento);

        restBeneficiamentoMockMvc.perform(post("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficiamentos() throws Exception {
        // Initialize the database
        beneficiamentoRepository.saveAndFlush(beneficiamento);

        // Get all the beneficiamentoList
        restBeneficiamentoMockMvc.perform(get("/api/beneficiamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].unidade").value(hasItem(DEFAULT_UNIDADE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getBeneficiamento() throws Exception {
        // Initialize the database
        beneficiamentoRepository.saveAndFlush(beneficiamento);

        // Get the beneficiamento
        restBeneficiamentoMockMvc.perform(get("/api/beneficiamentos/{id}", beneficiamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiamento.getId().intValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()))
            .andExpect(jsonPath("$.unidade").value(DEFAULT_UNIDADE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficiamento() throws Exception {
        // Get the beneficiamento
        restBeneficiamentoMockMvc.perform(get("/api/beneficiamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiamento() throws Exception {
        // Initialize the database
        beneficiamentoRepository.saveAndFlush(beneficiamento);

        int databaseSizeBeforeUpdate = beneficiamentoRepository.findAll().size();

        // Update the beneficiamento
        Beneficiamento updatedBeneficiamento = beneficiamentoRepository.findById(beneficiamento.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiamento are not directly saved in db
        em.detach(updatedBeneficiamento);
        updatedBeneficiamento
            .periodo(UPDATED_PERIODO)
            .quantidade(UPDATED_QUANTIDADE)
            .unidade(UPDATED_UNIDADE)
            .total(UPDATED_TOTAL);
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(updatedBeneficiamento);

        restBeneficiamentoMockMvc.perform(put("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Beneficiamento in the database
        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeUpdate);
        Beneficiamento testBeneficiamento = beneficiamentoList.get(beneficiamentoList.size() - 1);
        assertThat(testBeneficiamento.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testBeneficiamento.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testBeneficiamento.getUnidade()).isEqualTo(UPDATED_UNIDADE);
        assertThat(testBeneficiamento.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiamento() throws Exception {
        int databaseSizeBeforeUpdate = beneficiamentoRepository.findAll().size();

        // Create the Beneficiamento
        BeneficiamentoDTO beneficiamentoDTO = beneficiamentoMapper.toDto(beneficiamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiamentoMockMvc.perform(put("/api/beneficiamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiamento in the database
        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeneficiamento() throws Exception {
        // Initialize the database
        beneficiamentoRepository.saveAndFlush(beneficiamento);

        int databaseSizeBeforeDelete = beneficiamentoRepository.findAll().size();

        // Delete the beneficiamento
        restBeneficiamentoMockMvc.perform(delete("/api/beneficiamentos/{id}", beneficiamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Beneficiamento> beneficiamentoList = beneficiamentoRepository.findAll();
        assertThat(beneficiamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beneficiamento.class);
        Beneficiamento beneficiamento1 = new Beneficiamento();
        beneficiamento1.setId(1L);
        Beneficiamento beneficiamento2 = new Beneficiamento();
        beneficiamento2.setId(beneficiamento1.getId());
        assertThat(beneficiamento1).isEqualTo(beneficiamento2);
        beneficiamento2.setId(2L);
        assertThat(beneficiamento1).isNotEqualTo(beneficiamento2);
        beneficiamento1.setId(null);
        assertThat(beneficiamento1).isNotEqualTo(beneficiamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiamentoDTO.class);
        BeneficiamentoDTO beneficiamentoDTO1 = new BeneficiamentoDTO();
        beneficiamentoDTO1.setId(1L);
        BeneficiamentoDTO beneficiamentoDTO2 = new BeneficiamentoDTO();
        assertThat(beneficiamentoDTO1).isNotEqualTo(beneficiamentoDTO2);
        beneficiamentoDTO2.setId(beneficiamentoDTO1.getId());
        assertThat(beneficiamentoDTO1).isEqualTo(beneficiamentoDTO2);
        beneficiamentoDTO2.setId(2L);
        assertThat(beneficiamentoDTO1).isNotEqualTo(beneficiamentoDTO2);
        beneficiamentoDTO1.setId(null);
        assertThat(beneficiamentoDTO1).isNotEqualTo(beneficiamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(beneficiamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(beneficiamentoMapper.fromId(null)).isNull();
    }
}
