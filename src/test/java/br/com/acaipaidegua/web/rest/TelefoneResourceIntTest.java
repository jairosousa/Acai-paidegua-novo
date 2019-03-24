package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.Telefone;
import br.com.acaipaidegua.repository.TelefoneRepository;
import br.com.acaipaidegua.service.TelefoneService;
import br.com.acaipaidegua.service.dto.TelefoneDTO;
import br.com.acaipaidegua.service.mapper.TelefoneMapper;
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

import br.com.acaipaidegua.domain.enumeration.Operadora;
/**
 * Test class for the TelefoneResource REST controller.
 *
 * @see TelefoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class TelefoneResourceIntTest {

    private static final Operadora DEFAULT_OPERADORA = Operadora.CLARO;
    private static final Operadora UPDATED_OPERADORA = Operadora.OI;

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneMapper telefoneMapper;

    @Autowired
    private TelefoneService telefoneService;

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

    private MockMvc restTelefoneMockMvc;

    private Telefone telefone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TelefoneResource telefoneResource = new TelefoneResource(telefoneService);
        this.restTelefoneMockMvc = MockMvcBuilders.standaloneSetup(telefoneResource)
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
    public static Telefone createEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .operadora(DEFAULT_OPERADORA)
            .numero(DEFAULT_NUMERO);
        return telefone;
    }

    @Before
    public void initTest() {
        telefone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelefone() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getOperadora()).isEqualTo(DEFAULT_OPERADORA);
        assertThat(testTelefone.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createTelefoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // Create the Telefone with an existing ID
        telefone.setId(1L);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = telefoneRepository.findAll().size();
        // set the field null
        telefone.setNumero(null);

        // Create the Telefone, which fails.
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTelefones() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList
        restTelefoneMockMvc.perform(get("/api/telefones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().intValue())))
            .andExpect(jsonPath("$.[*].operadora").value(hasItem(DEFAULT_OPERADORA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())));
    }
    
    @Test
    @Transactional
    public void getTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(telefone.getId().intValue()))
            .andExpect(jsonPath("$.operadora").value(DEFAULT_OPERADORA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTelefone() throws Exception {
        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone
        Telefone updatedTelefone = telefoneRepository.findById(telefone.getId()).get();
        // Disconnect from session so that the updates on updatedTelefone are not directly saved in db
        em.detach(updatedTelefone);
        updatedTelefone
            .operadora(UPDATED_OPERADORA)
            .numero(UPDATED_NUMERO);
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(updatedTelefone);

        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getOperadora()).isEqualTo(UPDATED_OPERADORA);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Create the Telefone
        TelefoneDTO telefoneDTO = telefoneMapper.toDto(telefone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telefoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        int databaseSizeBeforeDelete = telefoneRepository.findAll().size();

        // Delete the telefone
        restTelefoneMockMvc.perform(delete("/api/telefones/{id}", telefone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefone.class);
        Telefone telefone1 = new Telefone();
        telefone1.setId(1L);
        Telefone telefone2 = new Telefone();
        telefone2.setId(telefone1.getId());
        assertThat(telefone1).isEqualTo(telefone2);
        telefone2.setId(2L);
        assertThat(telefone1).isNotEqualTo(telefone2);
        telefone1.setId(null);
        assertThat(telefone1).isNotEqualTo(telefone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelefoneDTO.class);
        TelefoneDTO telefoneDTO1 = new TelefoneDTO();
        telefoneDTO1.setId(1L);
        TelefoneDTO telefoneDTO2 = new TelefoneDTO();
        assertThat(telefoneDTO1).isNotEqualTo(telefoneDTO2);
        telefoneDTO2.setId(telefoneDTO1.getId());
        assertThat(telefoneDTO1).isEqualTo(telefoneDTO2);
        telefoneDTO2.setId(2L);
        assertThat(telefoneDTO1).isNotEqualTo(telefoneDTO2);
        telefoneDTO1.setId(null);
        assertThat(telefoneDTO1).isNotEqualTo(telefoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(telefoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(telefoneMapper.fromId(null)).isNull();
    }
}
