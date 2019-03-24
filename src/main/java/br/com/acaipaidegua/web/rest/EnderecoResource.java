package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.EnderecoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.EnderecoDTO;
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
 * REST controller for managing Endereco.
 */
@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "endereco";

    private final EnderecoService enderecoService;

    public EnderecoResource(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    /**
     * POST  /enderecos : Create a new endereco.
     *
     * @param enderecoDTO the enderecoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enderecoDTO, or with status 400 (Bad Request) if the endereco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> createEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() != null) {
            throw new BadRequestAlertException("A new endereco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enderecos : Updates an existing endereco.
     *
     * @param enderecoDTO the enderecoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enderecoDTO,
     * or with status 400 (Bad Request) if the enderecoDTO is not valid,
     * or with status 500 (Internal Server Error) if the enderecoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> updateEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enderecoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enderecos : get all the enderecos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enderecos in body
     */
    @GetMapping("/enderecos")
    public List<EnderecoDTO> getAllEnderecos() {
        log.debug("REST request to get all Enderecos");
        return enderecoService.findAll();
    }

    /**
     * GET  /enderecos/:id : get the "id" endereco.
     *
     * @param id the id of the enderecoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enderecoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTO> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<EnderecoDTO> enderecoDTO = enderecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoDTO);
    }

    /**
     * DELETE  /enderecos/:id : delete the "id" endereco.
     *
     * @param id the id of the enderecoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
