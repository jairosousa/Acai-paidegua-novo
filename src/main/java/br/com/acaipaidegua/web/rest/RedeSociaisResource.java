package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.RedeSociaisService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.RedeSociaisDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RedeSociais.
 */
@RestController
@RequestMapping("/api")
public class RedeSociaisResource {

    private final Logger log = LoggerFactory.getLogger(RedeSociaisResource.class);

    private static final String ENTITY_NAME = "redeSociais";

    private final RedeSociaisService redeSociaisService;

    public RedeSociaisResource(RedeSociaisService redeSociaisService) {
        this.redeSociaisService = redeSociaisService;
    }

    /**
     * POST  /rede-sociais : Create a new redeSociais.
     *
     * @param redeSociaisDTO the redeSociaisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new redeSociaisDTO, or with status 400 (Bad Request) if the redeSociais has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rede-sociais")
    public ResponseEntity<RedeSociaisDTO> createRedeSociais(@Valid @RequestBody RedeSociaisDTO redeSociaisDTO) throws URISyntaxException {
        log.debug("REST request to save RedeSociais : {}", redeSociaisDTO);
        if (redeSociaisDTO.getId() != null) {
            throw new BadRequestAlertException("A new redeSociais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RedeSociaisDTO result = redeSociaisService.save(redeSociaisDTO);
        return ResponseEntity.created(new URI("/api/rede-sociais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rede-sociais : Updates an existing redeSociais.
     *
     * @param redeSociaisDTO the redeSociaisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated redeSociaisDTO,
     * or with status 400 (Bad Request) if the redeSociaisDTO is not valid,
     * or with status 500 (Internal Server Error) if the redeSociaisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rede-sociais")
    public ResponseEntity<RedeSociaisDTO> updateRedeSociais(@Valid @RequestBody RedeSociaisDTO redeSociaisDTO) throws URISyntaxException {
        log.debug("REST request to update RedeSociais : {}", redeSociaisDTO);
        if (redeSociaisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RedeSociaisDTO result = redeSociaisService.save(redeSociaisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, redeSociaisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rede-sociais : get all the redeSociais.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of redeSociais in body
     */
    @GetMapping("/rede-sociais")
    public List<RedeSociaisDTO> getAllRedeSociais() {
        log.debug("REST request to get all RedeSociais");
        return redeSociaisService.findAll();
    }

    /**
     * GET  /rede-sociais/:id : get the "id" redeSociais.
     *
     * @param id the id of the redeSociaisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the redeSociaisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rede-sociais/{id}")
    public ResponseEntity<RedeSociaisDTO> getRedeSociais(@PathVariable Long id) {
        log.debug("REST request to get RedeSociais : {}", id);
        Optional<RedeSociaisDTO> redeSociaisDTO = redeSociaisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(redeSociaisDTO);
    }

    /**
     * DELETE  /rede-sociais/:id : delete the "id" redeSociais.
     *
     * @param id the id of the redeSociaisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rede-sociais/{id}")
    public ResponseEntity<Void> deleteRedeSociais(@PathVariable Long id) {
        log.debug("REST request to delete RedeSociais : {}", id);
        redeSociaisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
