package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.TabelaPrecoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.web.rest.util.PaginationUtil;
import br.com.acaipaidegua.service.dto.TabelaPrecoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TabelaPreco.
 */
@RestController
@RequestMapping("/api")
public class TabelaPrecoResource {

    private final Logger log = LoggerFactory.getLogger(TabelaPrecoResource.class);

    private static final String ENTITY_NAME = "tabelaPreco";

    private final TabelaPrecoService tabelaPrecoService;

    public TabelaPrecoResource(TabelaPrecoService tabelaPrecoService) {
        this.tabelaPrecoService = tabelaPrecoService;
    }

    /**
     * POST  /tabela-precos : Create a new tabelaPreco.
     *
     * @param tabelaPrecoDTO the tabelaPrecoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tabelaPrecoDTO, or with status 400 (Bad Request) if the tabelaPreco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tabela-precos")
    public ResponseEntity<TabelaPrecoDTO> createTabelaPreco(@Valid @RequestBody TabelaPrecoDTO tabelaPrecoDTO) throws URISyntaxException {
        log.debug("REST request to save TabelaPreco : {}", tabelaPrecoDTO);
        if (tabelaPrecoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tabelaPreco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TabelaPrecoDTO result = tabelaPrecoService.save(tabelaPrecoDTO);
        return ResponseEntity.created(new URI("/api/tabela-precos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tabela-precos : Updates an existing tabelaPreco.
     *
     * @param tabelaPrecoDTO the tabelaPrecoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tabelaPrecoDTO,
     * or with status 400 (Bad Request) if the tabelaPrecoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tabelaPrecoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tabela-precos")
    public ResponseEntity<TabelaPrecoDTO> updateTabelaPreco(@Valid @RequestBody TabelaPrecoDTO tabelaPrecoDTO) throws URISyntaxException {
        log.debug("REST request to update TabelaPreco : {}", tabelaPrecoDTO);
        if (tabelaPrecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TabelaPrecoDTO result = tabelaPrecoService.save(tabelaPrecoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tabelaPrecoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tabela-precos : get all the tabelaPrecos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tabelaPrecos in body
     */
    @GetMapping("/tabela-precos")
    public ResponseEntity<List<TabelaPrecoDTO>> getAllTabelaPrecos(Pageable pageable) {
        log.debug("REST request to get a page of TabelaPrecos");
        Page<TabelaPrecoDTO> page = tabelaPrecoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tabela-precos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tabela-precos/:id : get the "id" tabelaPreco.
     *
     * @param id the id of the tabelaPrecoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tabelaPrecoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tabela-precos/{id}")
    public ResponseEntity<TabelaPrecoDTO> getTabelaPreco(@PathVariable Long id) {
        log.debug("REST request to get TabelaPreco : {}", id);
        Optional<TabelaPrecoDTO> tabelaPrecoDTO = tabelaPrecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tabelaPrecoDTO);
    }

    /**
     * DELETE  /tabela-precos/:id : delete the "id" tabelaPreco.
     *
     * @param id the id of the tabelaPrecoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tabela-precos/{id}")
    public ResponseEntity<Void> deleteTabelaPreco(@PathVariable Long id) {
        log.debug("REST request to delete TabelaPreco : {}", id);
        tabelaPrecoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
