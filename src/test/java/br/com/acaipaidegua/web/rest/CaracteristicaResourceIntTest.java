package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.Caracteristica;
import br.com.acaipaidegua.repository.CaracteristicaRepository;
import br.com.acaipaidegua.service.CaracteristicaService;
import br.com.acaipaidegua.service.dto.CaracteristicaDTO;
import br.com.acaipaidegua.service.mapper.CaracteristicaMapper;
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
 * Test class for the CaracteristicaResource REST controller.
 *
 * @see CaracteristicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class CaracteristicaResourceIntTest {

    private static final Boolean DEFAULT_POSSUI_SELO = false;
    private static final Boolean UPDATED_POSSUI_SELO = true;

    private static final Boolean DEFAULT_POSSUI_RESTAURANTE = false;
    private static final Boolean UPDATED_POSSUI_RESTAURANTE = true;

    private static final Boolean DEFAULT_AREA_PRODUCAO_ISOLADA = false;
    private static final Boolean UPDATED_AREA_PRODUCAO_ISOLADA = true;

    private static final Boolean DEFAULT_COBRA_TAXA_ENTREGA = false;
    private static final Boolean UPDATED_COBRA_TAXA_ENTREGA = true;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Autowired
    private CaracteristicaMapper caracteristicaMapper;

    @Autowired
    private CaracteristicaService caracteristicaService;

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

    private MockMvc restCaracteristicaMockMvc;

    private Caracteristica caracteristica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaracteristicaResource caracteristicaResource = new CaracteristicaResource(caracteristicaService);
        this.restCaracteristicaMockMvc = MockMvcBuilders.standaloneSetup(caracteristicaResource)
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
    public static Caracteristica createEntity(EntityManager em) {
        Caracteristica caracteristica = new Caracteristica()
            .possuiSelo(DEFAULT_POSSUI_SELO)
            .possuiRestaurante(DEFAULT_POSSUI_RESTAURANTE)
            .areaProducaoIsolada(DEFAULT_AREA_PRODUCAO_ISOLADA)
            .cobraTaxaEntrega(DEFAULT_COBRA_TAXA_ENTREGA)
            .valor(DEFAULT_VALOR)
            .observacao(DEFAULT_OBSERVACAO);
        return caracteristica;
    }

    @Before
    public void initTest() {
        caracteristica = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristica() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);
        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeCreate + 1);
        Caracteristica testCaracteristica = caracteristicaList.get(caracteristicaList.size() - 1);
        assertThat(testCaracteristica.isPossuiSelo()).isEqualTo(DEFAULT_POSSUI_SELO);
        assertThat(testCaracteristica.isPossuiRestaurante()).isEqualTo(DEFAULT_POSSUI_RESTAURANTE);
        assertThat(testCaracteristica.isAreaProducaoIsolada()).isEqualTo(DEFAULT_AREA_PRODUCAO_ISOLADA);
        assertThat(testCaracteristica.isCobraTaxaEntrega()).isEqualTo(DEFAULT_COBRA_TAXA_ENTREGA);
        assertThat(testCaracteristica.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testCaracteristica.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    public void createCaracteristicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica with an existing ID
        caracteristica.setId(1L);
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPossuiSeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaRepository.findAll().size();
        // set the field null
        caracteristica.setPossuiSelo(null);

        // Create the Caracteristica, which fails.
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPossuiRestauranteIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaRepository.findAll().size();
        // set the field null
        caracteristica.setPossuiRestaurante(null);

        // Create the Caracteristica, which fails.
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAreaProducaoIsoladaIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaRepository.findAll().size();
        // set the field null
        caracteristica.setAreaProducaoIsolada(null);

        // Create the Caracteristica, which fails.
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCobraTaxaEntregaIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaRepository.findAll().size();
        // set the field null
        caracteristica.setCobraTaxaEntrega(null);

        // Create the Caracteristica, which fails.
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCaracteristicas() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].possuiSelo").value(hasItem(DEFAULT_POSSUI_SELO.booleanValue())))
            .andExpect(jsonPath("$.[*].possuiRestaurante").value(hasItem(DEFAULT_POSSUI_RESTAURANTE.booleanValue())))
            .andExpect(jsonPath("$.[*].areaProducaoIsolada").value(hasItem(DEFAULT_AREA_PRODUCAO_ISOLADA.booleanValue())))
            .andExpect(jsonPath("$.[*].cobraTaxaEntrega").value(hasItem(DEFAULT_COBRA_TAXA_ENTREGA.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/{id}", caracteristica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristica.getId().intValue()))
            .andExpect(jsonPath("$.possuiSelo").value(DEFAULT_POSSUI_SELO.booleanValue()))
            .andExpect(jsonPath("$.possuiRestaurante").value(DEFAULT_POSSUI_RESTAURANTE.booleanValue()))
            .andExpect(jsonPath("$.areaProducaoIsolada").value(DEFAULT_AREA_PRODUCAO_ISOLADA.booleanValue()))
            .andExpect(jsonPath("$.cobraTaxaEntrega").value(DEFAULT_COBRA_TAXA_ENTREGA.booleanValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaracteristica() throws Exception {
        // Get the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        int databaseSizeBeforeUpdate = caracteristicaRepository.findAll().size();

        // Update the caracteristica
        Caracteristica updatedCaracteristica = caracteristicaRepository.findById(caracteristica.getId()).get();
        // Disconnect from session so that the updates on updatedCaracteristica are not directly saved in db
        em.detach(updatedCaracteristica);
        updatedCaracteristica
            .possuiSelo(UPDATED_POSSUI_SELO)
            .possuiRestaurante(UPDATED_POSSUI_RESTAURANTE)
            .areaProducaoIsolada(UPDATED_AREA_PRODUCAO_ISOLADA)
            .cobraTaxaEntrega(UPDATED_COBRA_TAXA_ENTREGA)
            .valor(UPDATED_VALOR)
            .observacao(UPDATED_OBSERVACAO);
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(updatedCaracteristica);

        restCaracteristicaMockMvc.perform(put("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isOk());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeUpdate);
        Caracteristica testCaracteristica = caracteristicaList.get(caracteristicaList.size() - 1);
        assertThat(testCaracteristica.isPossuiSelo()).isEqualTo(UPDATED_POSSUI_SELO);
        assertThat(testCaracteristica.isPossuiRestaurante()).isEqualTo(UPDATED_POSSUI_RESTAURANTE);
        assertThat(testCaracteristica.isAreaProducaoIsolada()).isEqualTo(UPDATED_AREA_PRODUCAO_ISOLADA);
        assertThat(testCaracteristica.isCobraTaxaEntrega()).isEqualTo(UPDATED_COBRA_TAXA_ENTREGA);
        assertThat(testCaracteristica.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testCaracteristica.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristica() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicaMockMvc.perform(put("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        int databaseSizeBeforeDelete = caracteristicaRepository.findAll().size();

        // Delete the caracteristica
        restCaracteristicaMockMvc.perform(delete("/api/caracteristicas/{id}", caracteristica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caracteristica.class);
        Caracteristica caracteristica1 = new Caracteristica();
        caracteristica1.setId(1L);
        Caracteristica caracteristica2 = new Caracteristica();
        caracteristica2.setId(caracteristica1.getId());
        assertThat(caracteristica1).isEqualTo(caracteristica2);
        caracteristica2.setId(2L);
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
        caracteristica1.setId(null);
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicaDTO.class);
        CaracteristicaDTO caracteristicaDTO1 = new CaracteristicaDTO();
        caracteristicaDTO1.setId(1L);
        CaracteristicaDTO caracteristicaDTO2 = new CaracteristicaDTO();
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
        caracteristicaDTO2.setId(caracteristicaDTO1.getId());
        assertThat(caracteristicaDTO1).isEqualTo(caracteristicaDTO2);
        caracteristicaDTO2.setId(2L);
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
        caracteristicaDTO1.setId(null);
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(caracteristicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(caracteristicaMapper.fromId(null)).isNull();
    }
}
