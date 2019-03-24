package br.com.acaipaidegua.web.rest;

import br.com.acaipaidegua.NewacaipaideguaApp;

import br.com.acaipaidegua.domain.Imagem;
import br.com.acaipaidegua.repository.ImagemRepository;
import br.com.acaipaidegua.service.ImagemService;
import br.com.acaipaidegua.service.dto.ImagemDTO;
import br.com.acaipaidegua.service.mapper.ImagemMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static br.com.acaipaidegua.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImagemResource REST controller.
 *
 * @see ImagemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewacaipaideguaApp.class)
public class ImagemResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_UPLOADED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOADED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private ImagemMapper imagemMapper;

    @Autowired
    private ImagemService imagemService;

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

    private MockMvc restImagemMockMvc;

    private Imagem imagem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImagemResource imagemResource = new ImagemResource(imagemService);
        this.restImagemMockMvc = MockMvcBuilders.standaloneSetup(imagemResource)
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
    public static Imagem createEntity(EntityManager em) {
        Imagem imagem = new Imagem()
            .title(DEFAULT_TITLE)
            .descricao(DEFAULT_DESCRICAO)
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .uploaded(DEFAULT_UPLOADED);
        return imagem;
    }

    @Before
    public void initTest() {
        imagem = createEntity(em);
    }

    @Test
    @Transactional
    public void createImagem() throws Exception {
        int databaseSizeBeforeCreate = imagemRepository.findAll().size();

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);
        restImagemMockMvc.perform(post("/api/imagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isCreated());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeCreate + 1);
        Imagem testImagem = imagemList.get(imagemList.size() - 1);
        assertThat(testImagem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testImagem.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testImagem.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testImagem.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testImagem.getUploaded()).isEqualTo(DEFAULT_UPLOADED);
    }

    @Test
    @Transactional
    public void createImagemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imagemRepository.findAll().size();

        // Create the Imagem with an existing ID
        imagem.setId(1L);
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImagemMockMvc.perform(post("/api/imagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImagems() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get all the imagemList
        restImagemMockMvc.perform(get("/api/imagems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imagem.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].uploaded").value(hasItem(DEFAULT_UPLOADED.toString())));
    }
    
    @Test
    @Transactional
    public void getImagem() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        // Get the imagem
        restImagemMockMvc.perform(get("/api/imagems/{id}", imagem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imagem.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.uploaded").value(DEFAULT_UPLOADED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImagem() throws Exception {
        // Get the imagem
        restImagemMockMvc.perform(get("/api/imagems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImagem() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();

        // Update the imagem
        Imagem updatedImagem = imagemRepository.findById(imagem.getId()).get();
        // Disconnect from session so that the updates on updatedImagem are not directly saved in db
        em.detach(updatedImagem);
        updatedImagem
            .title(UPDATED_TITLE)
            .descricao(UPDATED_DESCRICAO)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .uploaded(UPDATED_UPLOADED);
        ImagemDTO imagemDTO = imagemMapper.toDto(updatedImagem);

        restImagemMockMvc.perform(put("/api/imagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isOk());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
        Imagem testImagem = imagemList.get(imagemList.size() - 1);
        assertThat(testImagem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testImagem.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testImagem.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testImagem.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testImagem.getUploaded()).isEqualTo(UPDATED_UPLOADED);
    }

    @Test
    @Transactional
    public void updateNonExistingImagem() throws Exception {
        int databaseSizeBeforeUpdate = imagemRepository.findAll().size();

        // Create the Imagem
        ImagemDTO imagemDTO = imagemMapper.toDto(imagem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImagemMockMvc.perform(put("/api/imagems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imagemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imagem in the database
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImagem() throws Exception {
        // Initialize the database
        imagemRepository.saveAndFlush(imagem);

        int databaseSizeBeforeDelete = imagemRepository.findAll().size();

        // Delete the imagem
        restImagemMockMvc.perform(delete("/api/imagems/{id}", imagem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Imagem> imagemList = imagemRepository.findAll();
        assertThat(imagemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imagem.class);
        Imagem imagem1 = new Imagem();
        imagem1.setId(1L);
        Imagem imagem2 = new Imagem();
        imagem2.setId(imagem1.getId());
        assertThat(imagem1).isEqualTo(imagem2);
        imagem2.setId(2L);
        assertThat(imagem1).isNotEqualTo(imagem2);
        imagem1.setId(null);
        assertThat(imagem1).isNotEqualTo(imagem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagemDTO.class);
        ImagemDTO imagemDTO1 = new ImagemDTO();
        imagemDTO1.setId(1L);
        ImagemDTO imagemDTO2 = new ImagemDTO();
        assertThat(imagemDTO1).isNotEqualTo(imagemDTO2);
        imagemDTO2.setId(imagemDTO1.getId());
        assertThat(imagemDTO1).isEqualTo(imagemDTO2);
        imagemDTO2.setId(2L);
        assertThat(imagemDTO1).isNotEqualTo(imagemDTO2);
        imagemDTO1.setId(null);
        assertThat(imagemDTO1).isNotEqualTo(imagemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imagemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imagemMapper.fromId(null)).isNull();
    }
}
