package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.ImagemService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.ImagemDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Imagem.
 */
@RestController
@RequestMapping("/api")
public class ImagemResource {

    private final Logger log = LoggerFactory.getLogger(ImagemResource.class);

    private static final String ENTITY_NAME = "imagem";

    private final ImagemService imagemService;

    public ImagemResource(ImagemService imagemService) {
        this.imagemService = imagemService;
    }

    /**
     * POST  /imagems : Create a new imagem.
     *
     * @param imagemDTO the imagemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imagemDTO, or with status 400 (Bad Request) if the imagem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/imagems")
    public ResponseEntity<ImagemDTO> createImagem(@RequestBody ImagemDTO imagemDTO) throws URISyntaxException {
        log.debug("REST request to save Imagem : {}", imagemDTO);
        if (imagemDTO.getId() != null) {
            throw new BadRequestAlertException("A new imagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImagemDTO result = imagemService.save(imagemDTO);
        return ResponseEntity.created(new URI("/api/imagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imagems : Updates an existing imagem.
     *
     * @param imagemDTO the imagemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imagemDTO,
     * or with status 400 (Bad Request) if the imagemDTO is not valid,
     * or with status 500 (Internal Server Error) if the imagemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/imagems")
    public ResponseEntity<ImagemDTO> updateImagem(@RequestBody ImagemDTO imagemDTO) throws URISyntaxException {
        log.debug("REST request to update Imagem : {}", imagemDTO);
        if (imagemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImagemDTO result = imagemService.save(imagemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imagemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imagems : get all the imagems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of imagems in body
     */
    @GetMapping("/imagems")
    public List<ImagemDTO> getAllImagems() {
        log.debug("REST request to get all Imagems");
        return imagemService.findAll();
    }

    /**
     * GET  /imagems/:id : get the "id" imagem.
     *
     * @param id the id of the imagemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imagemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/imagems/{id}")
    public ResponseEntity<ImagemDTO> getImagem(@PathVariable Long id) {
        log.debug("REST request to get Imagem : {}", id);
        Optional<ImagemDTO> imagemDTO = imagemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imagemDTO);
    }

    /**
     * DELETE  /imagems/:id : delete the "id" imagem.
     *
     * @param id the id of the imagemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/imagems/{id}")
    public ResponseEntity<Void> deleteImagem(@PathVariable Long id) {
        log.debug("REST request to delete Imagem : {}", id);
        imagemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
