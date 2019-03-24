package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.ResiduoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.ResiduoDTO;
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
 * REST controller for managing Residuo.
 */
@RestController
@RequestMapping("/api")
public class ResiduoResource {

    private final Logger log = LoggerFactory.getLogger(ResiduoResource.class);

    private static final String ENTITY_NAME = "residuo";

    private final ResiduoService residuoService;

    public ResiduoResource(ResiduoService residuoService) {
        this.residuoService = residuoService;
    }

    /**
     * POST  /residuos : Create a new residuo.
     *
     * @param residuoDTO the residuoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new residuoDTO, or with status 400 (Bad Request) if the residuo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/residuos")
    public ResponseEntity<ResiduoDTO> createResiduo(@Valid @RequestBody ResiduoDTO residuoDTO) throws URISyntaxException {
        log.debug("REST request to save Residuo : {}", residuoDTO);
        if (residuoDTO.getId() != null) {
            throw new BadRequestAlertException("A new residuo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResiduoDTO result = residuoService.save(residuoDTO);
        return ResponseEntity.created(new URI("/api/residuos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /residuos : Updates an existing residuo.
     *
     * @param residuoDTO the residuoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated residuoDTO,
     * or with status 400 (Bad Request) if the residuoDTO is not valid,
     * or with status 500 (Internal Server Error) if the residuoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/residuos")
    public ResponseEntity<ResiduoDTO> updateResiduo(@Valid @RequestBody ResiduoDTO residuoDTO) throws URISyntaxException {
        log.debug("REST request to update Residuo : {}", residuoDTO);
        if (residuoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResiduoDTO result = residuoService.save(residuoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, residuoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /residuos : get all the residuos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of residuos in body
     */
    @GetMapping("/residuos")
    public List<ResiduoDTO> getAllResiduos() {
        log.debug("REST request to get all Residuos");
        return residuoService.findAll();
    }

    /**
     * GET  /residuos/:id : get the "id" residuo.
     *
     * @param id the id of the residuoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the residuoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/residuos/{id}")
    public ResponseEntity<ResiduoDTO> getResiduo(@PathVariable Long id) {
        log.debug("REST request to get Residuo : {}", id);
        Optional<ResiduoDTO> residuoDTO = residuoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(residuoDTO);
    }

    /**
     * DELETE  /residuos/:id : delete the "id" residuo.
     *
     * @param id the id of the residuoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/residuos/{id}")
    public ResponseEntity<Void> deleteResiduo(@PathVariable Long id) {
        log.debug("REST request to delete Residuo : {}", id);
        residuoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
