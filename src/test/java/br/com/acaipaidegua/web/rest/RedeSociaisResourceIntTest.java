package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.RedeSociais;
import br.com.acaipaidegua.repository.RedeSociaisRepository;
import br.com.acaipaidegua.service.RedeSociaisService;
import br.com.acaipaidegua.service.dto.RedeSociaisDTO;
import br.com.acaipaidegua.service.mapper.RedeSociaisMapper;
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

import br.com.acaipaidegua.domain.enumeration.RedeSocial;
/**
 * Test class for the RedeSociaisResource REST controller.
 *
 * @see RedeSociaisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class RedeSociaisResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final RedeSocial DEFAULT_NOME = RedeSocial.LINKEDIN;
    private static final RedeSocial UPDATED_NOME = RedeSocial.FACEBOOK;

    @Autowired
    private RedeSociaisRepository redeSociaisRepository;

    @Autowired
    private RedeSociaisMapper redeSociaisMapper;

    @Autowired
    private RedeSociaisService redeSociaisService;

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

    private MockMvc restRedeSociaisMockMvc;

    private RedeSociais redeSociais;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RedeSociaisResource redeSociaisResource = new RedeSociaisResource(redeSociaisService);
        this.restRedeSociaisMockMvc = MockMvcBuilders.standaloneSetup(redeSociaisResource)
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
    public static RedeSociais createEntity(EntityManager em) {
        RedeSociais redeSociais = new RedeSociais()
            .url(DEFAULT_URL)
            .nome(DEFAULT_NOME);
        return redeSociais;
    }

    @Before
    public void initTest() {
        redeSociais = createEntity(em);
    }

    @Test
    @Transactional
    public void createRedeSociais() throws Exception {
        int databaseSizeBeforeCreate = redeSociaisRepository.findAll().size();

        // Create the RedeSociais
        RedeSociaisDTO redeSociaisDTO = redeSociaisMapper.toDto(redeSociais);
        restRedeSociaisMockMvc.perform(post("/api/rede-sociais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(redeSociaisDTO)))
            .andExpect(status().isCreated());

        // Validate the RedeSociais in the database
        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeCreate + 1);
        RedeSociais testRedeSociais = redeSociaisList.get(redeSociaisList.size() - 1);
        assertThat(testRedeSociais.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testRedeSociais.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createRedeSociaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = redeSociaisRepository.findAll().size();

        // Create the RedeSociais with an existing ID
        redeSociais.setId(1L);
        RedeSociaisDTO redeSociaisDTO = redeSociaisMapper.toDto(redeSociais);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRedeSociaisMockMvc.perform(post("/api/rede-sociais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(redeSociaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RedeSociais in the database
        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeSociaisRepository.findAll().size();
        // set the field null
        redeSociais.setUrl(null);

        // Create the RedeSociais, which fails.
        RedeSociaisDTO redeSociaisDTO = redeSociaisMapper.toDto(redeSociais);

        restRedeSociaisMockMvc.perform(post("/api/rede-sociais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(redeSociaisDTO)))
            .andExpect(status().isBadRequest());

        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = redeSociaisRepository.findAll().size();
        // set the field null
        redeSociais.setNome(null);

        // Create the RedeSociais, which fails.
        RedeSociaisDTO redeSociaisDTO = redeSociaisMapper.toDto(redeSociais);

        restRedeSociaisMockMvc.perform(post("/api/rede-sociais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(redeSociaisDTO)))
            .andExpect(status().isBadRequest());

        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRedeSociais() throws Exception {
        // Initialize the database
        redeSociaisRepository.saveAndFlush(redeSociais);

        // Get all the redeSociaisList
        restRedeSociaisMockMvc.perform(get("/api/rede-sociais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(redeSociais.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getRedeSociais() throws Exception {
        // Initialize the database
        redeSociaisRepository.saveAndFlush(redeSociais);

        // Get the redeSociais
        restRedeSociaisMockMvc.perform(get("/api/rede-sociais/{id}", redeSociais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(redeSociais.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRedeSociais() throws Exception {
        // Get the redeSociais
        restRedeSociaisMockMvc.perform(get("/api/rede-sociais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRedeSociais() throws Exception {
        // Initialize the database
        redeSociaisRepository.saveAndFlush(redeSociais);

        int databaseSizeBeforeUpdate = redeSociaisRepository.findAll().size();

        // Update the redeSociais
        RedeSociais updatedRedeSociais = redeSociaisRepository.findById(redeSociais.getId()).get();
        // Disconnect from session so that the updates on updatedRedeSociais are not directly saved in db
        em.detach(updatedRedeSociais);
        updatedRedeSociais
            .url(UPDATED_URL)
            .nome(UPDATED_NOME);
        RedeSociaisDTO redeSociaisDTO = redeSociaisMapper.toDto(updatedRedeSociais);

        restRedeSociaisMockMvc.perform(put("/api/rede-sociais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(redeSociaisDTO)))
            .andExpect(status().isOk());

        // Validate the RedeSociais in the database
        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeUpdate);
        RedeSociais testRedeSociais = redeSociaisList.get(redeSociaisList.size() - 1);
        assertThat(testRedeSociais.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testRedeSociais.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingRedeSociais() throws Exception {
        int databaseSizeBeforeUpdate = redeSociaisRepository.findAll().size();

        // Create the RedeSociais
        RedeSociaisDTO redeSociaisDTO = redeSociaisMapper.toDto(redeSociais);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRedeSociaisMockMvc.perform(put("/api/rede-sociais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(redeSociaisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RedeSociais in the database
        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRedeSociais() throws Exception {
        // Initialize the database
        redeSociaisRepository.saveAndFlush(redeSociais);

        int databaseSizeBeforeDelete = redeSociaisRepository.findAll().size();

        // Delete the redeSociais
        restRedeSociaisMockMvc.perform(delete("/api/rede-sociais/{id}", redeSociais.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RedeSociais> redeSociaisList = redeSociaisRepository.findAll();
        assertThat(redeSociaisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RedeSociais.class);
        RedeSociais redeSociais1 = new RedeSociais();
        redeSociais1.setId(1L);
        RedeSociais redeSociais2 = new RedeSociais();
        redeSociais2.setId(redeSociais1.getId());
        assertThat(redeSociais1).isEqualTo(redeSociais2);
        redeSociais2.setId(2L);
        assertThat(redeSociais1).isNotEqualTo(redeSociais2);
        redeSociais1.setId(null);
        assertThat(redeSociais1).isNotEqualTo(redeSociais2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RedeSociaisDTO.class);
        RedeSociaisDTO redeSociaisDTO1 = new RedeSociaisDTO();
        redeSociaisDTO1.setId(1L);
        RedeSociaisDTO redeSociaisDTO2 = new RedeSociaisDTO();
        assertThat(redeSociaisDTO1).isNotEqualTo(redeSociaisDTO2);
        redeSociaisDTO2.setId(redeSociaisDTO1.getId());
        assertThat(redeSociaisDTO1).isEqualTo(redeSociaisDTO2);
        redeSociaisDTO2.setId(2L);
        assertThat(redeSociaisDTO1).isNotEqualTo(redeSociaisDTO2);
        redeSociaisDTO1.setId(null);
        assertThat(redeSociaisDTO1).isNotEqualTo(redeSociaisDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(redeSociaisMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(redeSociaisMapper.fromId(null)).isNull();
    }
}
