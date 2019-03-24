package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.TelefoneService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.TelefoneDTO;
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
 * REST controller for managing Telefone.
 */
@RestController
@RequestMapping("/api")
public class TelefoneResource {

    private final Logger log = LoggerFactory.getLogger(TelefoneResource.class);

    private static final String ENTITY_NAME = "telefone";

    private final TelefoneService telefoneService;

    public TelefoneResource(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    /**
     * POST  /telefones : Create a new telefone.
     *
     * @param telefoneDTO the telefoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new telefoneDTO, or with status 400 (Bad Request) if the telefone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/telefones")
    public ResponseEntity<TelefoneDTO> createTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        log.debug("REST request to save Telefone : {}", telefoneDTO);
        if (telefoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new telefone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelefoneDTO result = telefoneService.save(telefoneDTO);
        return ResponseEntity.created(new URI("/api/telefones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /telefones : Updates an existing telefone.
     *
     * @param telefoneDTO the telefoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated telefoneDTO,
     * or with status 400 (Bad Request) if the telefoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the telefoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/telefones")
    public ResponseEntity<TelefoneDTO> updateTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        log.debug("REST request to update Telefone : {}", telefoneDTO);
        if (telefoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TelefoneDTO result = telefoneService.save(telefoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, telefoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /telefones : get all the telefones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of telefones in body
     */
    @GetMapping("/telefones")
    public List<TelefoneDTO> getAllTelefones() {
        log.debug("REST request to get all Telefones");
        return telefoneService.findAll();
    }

    /**
     * GET  /telefones/:id : get the "id" telefone.
     *
     * @param id the id of the telefoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the telefoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/telefones/{id}")
    public ResponseEntity<TelefoneDTO> getTelefone(@PathVariable Long id) {
        log.debug("REST request to get Telefone : {}", id);
        Optional<TelefoneDTO> telefoneDTO = telefoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telefoneDTO);
    }

    /**
     * DELETE  /telefones/:id : delete the "id" telefone.
     *
     * @param id the id of the telefoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/telefones/{id}")
    public ResponseEntity<Void> deleteTelefone(@PathVariable Long id) {
        log.debug("REST request to delete Telefone : {}", id);
        telefoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
