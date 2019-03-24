package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.TabelaPreco;
import br.com.acaipaidegua.repository.TabelaPrecoRepository;
import br.com.acaipaidegua.service.TabelaPrecoService;
import br.com.acaipaidegua.service.dto.TabelaPrecoDTO;
import br.com.acaipaidegua.service.mapper.TabelaPrecoMapper;
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

/**
 * Test class for the TabelaPrecoResource REST controller.
 *
 * @see TabelaPrecoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class TabelaPrecoResourceIntTest {

    private static final BigDecimal DEFAULT_PRECO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO = new BigDecimal(2);

    @Autowired
    private TabelaPrecoRepository tabelaPrecoRepository;

    @Autowired
    private TabelaPrecoMapper tabelaPrecoMapper;

    @Autowired
    private TabelaPrecoService tabelaPrecoService;

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

    private MockMvc restTabelaPrecoMockMvc;

    private TabelaPreco tabelaPreco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TabelaPrecoResource tabelaPrecoResource = new TabelaPrecoResource(tabelaPrecoService);
        this.restTabelaPrecoMockMvc = MockMvcBuilders.standaloneSetup(tabelaPrecoResource)
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
    public static TabelaPreco createEntity(EntityManager em) {
        TabelaPreco tabelaPreco = new TabelaPreco()
            .preco(DEFAULT_PRECO);
        return tabelaPreco;
    }

    @Before
    public void initTest() {
        tabelaPreco = createEntity(em);
    }

    @Test
    @Transactional
    public void createTabelaPreco() throws Exception {
        int databaseSizeBeforeCreate = tabelaPrecoRepository.findAll().size();

        // Create the TabelaPreco
        TabelaPrecoDTO tabelaPrecoDTO = tabelaPrecoMapper.toDto(tabelaPreco);
        restTabelaPrecoMockMvc.perform(post("/api/tabela-precos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaPrecoDTO)))
            .andExpect(status().isCreated());

        // Validate the TabelaPreco in the database
        List<TabelaPreco> tabelaPrecoList = tabelaPrecoRepository.findAll();
        assertThat(tabelaPrecoList).hasSize(databaseSizeBeforeCreate + 1);
        TabelaPreco testTabelaPreco = tabelaPrecoList.get(tabelaPrecoList.size() - 1);
        assertThat(testTabelaPreco.getPreco()).isEqualTo(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void createTabelaPrecoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tabelaPrecoRepository.findAll().size();

        // Create the TabelaPreco with an existing ID
        tabelaPreco.setId(1L);
        TabelaPrecoDTO tabelaPrecoDTO = tabelaPrecoMapper.toDto(tabelaPreco);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabelaPrecoMockMvc.perform(post("/api/tabela-precos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaPrecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TabelaPreco in the database
        List<TabelaPreco> tabelaPrecoList = tabelaPrecoRepository.findAll();
        assertThat(tabelaPrecoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tabelaPrecoRepository.findAll().size();
        // set the field null
        tabelaPreco.setPreco(null);

        // Create the TabelaPreco, which fails.
        TabelaPrecoDTO tabelaPrecoDTO = tabelaPrecoMapper.toDto(tabelaPreco);

        restTabelaPrecoMockMvc.perform(post("/api/tabela-precos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaPrecoDTO)))
            .andExpect(status().isBadRequest());

        List<TabelaPreco> tabelaPrecoList = tabelaPrecoRepository.findAll();
        assertThat(tabelaPrecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTabelaPrecos() throws Exception {
        // Initialize the database
        tabelaPrecoRepository.saveAndFlush(tabelaPreco);

        // Get all the tabelaPrecoList
        restTabelaPrecoMockMvc.perform(get("/api/tabela-precos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tabelaPreco.getId().intValue())))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.intValue())));
    }
    
    @Test
    @Transactional
    public void getTabelaPreco() throws Exception {
        // Initialize the database
        tabelaPrecoRepository.saveAndFlush(tabelaPreco);

        // Get the tabelaPreco
        restTabelaPrecoMockMvc.perform(get("/api/tabela-precos/{id}", tabelaPreco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tabelaPreco.getId().intValue()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTabelaPreco() throws Exception {
        // Get the tabelaPreco
        restTabelaPrecoMockMvc.perform(get("/api/tabela-precos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTabelaPreco() throws Exception {
        // Initialize the database
        tabelaPrecoRepository.saveAndFlush(tabelaPreco);

        int databaseSizeBeforeUpdate = tabelaPrecoRepository.findAll().size();

        // Update the tabelaPreco
        TabelaPreco updatedTabelaPreco = tabelaPrecoRepository.findById(tabelaPreco.getId()).get();
        // Disconnect from session so that the updates on updatedTabelaPreco are not directly saved in db
        em.detach(updatedTabelaPreco);
        updatedTabelaPreco
            .preco(UPDATED_PRECO);
        TabelaPrecoDTO tabelaPrecoDTO = tabelaPrecoMapper.toDto(updatedTabelaPreco);

        restTabelaPrecoMockMvc.perform(put("/api/tabela-precos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaPrecoDTO)))
            .andExpect(status().isOk());

        // Validate the TabelaPreco in the database
        List<TabelaPreco> tabelaPrecoList = tabelaPrecoRepository.findAll();
        assertThat(tabelaPrecoList).hasSize(databaseSizeBeforeUpdate);
        TabelaPreco testTabelaPreco = tabelaPrecoList.get(tabelaPrecoList.size() - 1);
        assertThat(testTabelaPreco.getPreco()).isEqualTo(UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void updateNonExistingTabelaPreco() throws Exception {
        int databaseSizeBeforeUpdate = tabelaPrecoRepository.findAll().size();

        // Create the TabelaPreco
        TabelaPrecoDTO tabelaPrecoDTO = tabelaPrecoMapper.toDto(tabelaPreco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabelaPrecoMockMvc.perform(put("/api/tabela-precos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tabelaPrecoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TabelaPreco in the database
        List<TabelaPreco> tabelaPrecoList = tabelaPrecoRepository.findAll();
        assertThat(tabelaPrecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTabelaPreco() throws Exception {
        // Initialize the database
        tabelaPrecoRepository.saveAndFlush(tabelaPreco);

        int databaseSizeBeforeDelete = tabelaPrecoRepository.findAll().size();

        // Delete the tabelaPreco
        restTabelaPrecoMockMvc.perform(delete("/api/tabela-precos/{id}", tabelaPreco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TabelaPreco> tabelaPrecoList = tabelaPrecoRepository.findAll();
        assertThat(tabelaPrecoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabelaPreco.class);
        TabelaPreco tabelaPreco1 = new TabelaPreco();
        tabelaPreco1.setId(1L);
        TabelaPreco tabelaPreco2 = new TabelaPreco();
        tabelaPreco2.setId(tabelaPreco1.getId());
        assertThat(tabelaPreco1).isEqualTo(tabelaPreco2);
        tabelaPreco2.setId(2L);
        assertThat(tabelaPreco1).isNotEqualTo(tabelaPreco2);
        tabelaPreco1.setId(null);
        assertThat(tabelaPreco1).isNotEqualTo(tabelaPreco2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TabelaPrecoDTO.class);
        TabelaPrecoDTO tabelaPrecoDTO1 = new TabelaPrecoDTO();
        tabelaPrecoDTO1.setId(1L);
        TabelaPrecoDTO tabelaPrecoDTO2 = new TabelaPrecoDTO();
        assertThat(tabelaPrecoDTO1).isNotEqualTo(tabelaPrecoDTO2);
        tabelaPrecoDTO2.setId(tabelaPrecoDTO1.getId());
        assertThat(tabelaPrecoDTO1).isEqualTo(tabelaPrecoDTO2);
        tabelaPrecoDTO2.setId(2L);
        assertThat(tabelaPrecoDTO1).isNotEqualTo(tabelaPrecoDTO2);
        tabelaPrecoDTO1.setId(null);
        assertThat(tabelaPrecoDTO1).isNotEqualTo(tabelaPrecoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tabelaPrecoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tabelaPrecoMapper.fromId(null)).isNull();
    }
}
