package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.Geolocalizacao;
import br.com.acaipaidegua.repository.GeolocalizacaoRepository;
import br.com.acaipaidegua.service.GeolocalizacaoService;
import br.com.acaipaidegua.service.dto.GeolocalizacaoDTO;
import br.com.acaipaidegua.service.mapper.GeolocalizacaoMapper;
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

/**
 * Test class for the GeolocalizacaoResource REST controller.
 *
 * @see GeolocalizacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class GeolocalizacaoResourceIntTest {

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    @Autowired
    private GeolocalizacaoRepository geolocalizacaoRepository;

    @Autowired
    private GeolocalizacaoMapper geolocalizacaoMapper;

    @Autowired
    private GeolocalizacaoService geolocalizacaoService;

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

    private MockMvc restGeolocalizacaoMockMvc;

    private Geolocalizacao geolocalizacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeolocalizacaoResource geolocalizacaoResource = new GeolocalizacaoResource(geolocalizacaoService);
        this.restGeolocalizacaoMockMvc = MockMvcBuilders.standaloneSetup(geolocalizacaoResource)
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
    public static Geolocalizacao createEntity(EntityManager em) {
        Geolocalizacao geolocalizacao = new Geolocalizacao()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return geolocalizacao;
    }

    @Before
    public void initTest() {
        geolocalizacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeolocalizacao() throws Exception {
        int databaseSizeBeforeCreate = geolocalizacaoRepository.findAll().size();

        // Create the Geolocalizacao
        GeolocalizacaoDTO geolocalizacaoDTO = geolocalizacaoMapper.toDto(geolocalizacao);
        restGeolocalizacaoMockMvc.perform(post("/api/geolocalizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geolocalizacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Geolocalizacao in the database
        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Geolocalizacao testGeolocalizacao = geolocalizacaoList.get(geolocalizacaoList.size() - 1);
        assertThat(testGeolocalizacao.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGeolocalizacao.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createGeolocalizacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geolocalizacaoRepository.findAll().size();

        // Create the Geolocalizacao with an existing ID
        geolocalizacao.setId(1L);
        GeolocalizacaoDTO geolocalizacaoDTO = geolocalizacaoMapper.toDto(geolocalizacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeolocalizacaoMockMvc.perform(post("/api/geolocalizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geolocalizacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Geolocalizacao in the database
        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = geolocalizacaoRepository.findAll().size();
        // set the field null
        geolocalizacao.setLatitude(null);

        // Create the Geolocalizacao, which fails.
        GeolocalizacaoDTO geolocalizacaoDTO = geolocalizacaoMapper.toDto(geolocalizacao);

        restGeolocalizacaoMockMvc.perform(post("/api/geolocalizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geolocalizacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = geolocalizacaoRepository.findAll().size();
        // set the field null
        geolocalizacao.setLongitude(null);

        // Create the Geolocalizacao, which fails.
        GeolocalizacaoDTO geolocalizacaoDTO = geolocalizacaoMapper.toDto(geolocalizacao);

        restGeolocalizacaoMockMvc.perform(post("/api/geolocalizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geolocalizacaoDTO)))
            .andExpect(status().isBadRequest());

        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeolocalizacaos() throws Exception {
        // Initialize the database
        geolocalizacaoRepository.saveAndFlush(geolocalizacao);

        // Get all the geolocalizacaoList
        restGeolocalizacaoMockMvc.perform(get("/api/geolocalizacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geolocalizacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getGeolocalizacao() throws Exception {
        // Initialize the database
        geolocalizacaoRepository.saveAndFlush(geolocalizacao);

        // Get the geolocalizacao
        restGeolocalizacaoMockMvc.perform(get("/api/geolocalizacaos/{id}", geolocalizacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geolocalizacao.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeolocalizacao() throws Exception {
        // Get the geolocalizacao
        restGeolocalizacaoMockMvc.perform(get("/api/geolocalizacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeolocalizacao() throws Exception {
        // Initialize the database
        geolocalizacaoRepository.saveAndFlush(geolocalizacao);

        int databaseSizeBeforeUpdate = geolocalizacaoRepository.findAll().size();

        // Update the geolocalizacao
        Geolocalizacao updatedGeolocalizacao = geolocalizacaoRepository.findById(geolocalizacao.getId()).get();
        // Disconnect from session so that the updates on updatedGeolocalizacao are not directly saved in db
        em.detach(updatedGeolocalizacao);
        updatedGeolocalizacao
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        GeolocalizacaoDTO geolocalizacaoDTO = geolocalizacaoMapper.toDto(updatedGeolocalizacao);

        restGeolocalizacaoMockMvc.perform(put("/api/geolocalizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geolocalizacaoDTO)))
            .andExpect(status().isOk());

        // Validate the Geolocalizacao in the database
        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeUpdate);
        Geolocalizacao testGeolocalizacao = geolocalizacaoList.get(geolocalizacaoList.size() - 1);
        assertThat(testGeolocalizacao.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGeolocalizacao.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeolocalizacao() throws Exception {
        int databaseSizeBeforeUpdate = geolocalizacaoRepository.findAll().size();

        // Create the Geolocalizacao
        GeolocalizacaoDTO geolocalizacaoDTO = geolocalizacaoMapper.toDto(geolocalizacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeolocalizacaoMockMvc.perform(put("/api/geolocalizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geolocalizacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Geolocalizacao in the database
        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeolocalizacao() throws Exception {
        // Initialize the database
        geolocalizacaoRepository.saveAndFlush(geolocalizacao);

        int databaseSizeBeforeDelete = geolocalizacaoRepository.findAll().size();

        // Delete the geolocalizacao
        restGeolocalizacaoMockMvc.perform(delete("/api/geolocalizacaos/{id}", geolocalizacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Geolocalizacao> geolocalizacaoList = geolocalizacaoRepository.findAll();
        assertThat(geolocalizacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geolocalizacao.class);
        Geolocalizacao geolocalizacao1 = new Geolocalizacao();
        geolocalizacao1.setId(1L);
        Geolocalizacao geolocalizacao2 = new Geolocalizacao();
        geolocalizacao2.setId(geolocalizacao1.getId());
        assertThat(geolocalizacao1).isEqualTo(geolocalizacao2);
        geolocalizacao2.setId(2L);
        assertThat(geolocalizacao1).isNotEqualTo(geolocalizacao2);
        geolocalizacao1.setId(null);
        assertThat(geolocalizacao1).isNotEqualTo(geolocalizacao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeolocalizacaoDTO.class);
        GeolocalizacaoDTO geolocalizacaoDTO1 = new GeolocalizacaoDTO();
        geolocalizacaoDTO1.setId(1L);
        GeolocalizacaoDTO geolocalizacaoDTO2 = new GeolocalizacaoDTO();
        assertThat(geolocalizacaoDTO1).isNotEqualTo(geolocalizacaoDTO2);
        geolocalizacaoDTO2.setId(geolocalizacaoDTO1.getId());
        assertThat(geolocalizacaoDTO1).isEqualTo(geolocalizacaoDTO2);
        geolocalizacaoDTO2.setId(2L);
        assertThat(geolocalizacaoDTO1).isNotEqualTo(geolocalizacaoDTO2);
        geolocalizacaoDTO1.setId(null);
        assertThat(geolocalizacaoDTO1).isNotEqualTo(geolocalizacaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(geolocalizacaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(geolocalizacaoMapper.fromId(null)).isNull();
    }
}
