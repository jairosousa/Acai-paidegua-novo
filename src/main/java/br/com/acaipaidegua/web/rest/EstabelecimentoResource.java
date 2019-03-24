package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.EstabelecimentoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.web.rest.util.PaginationUtil;
import br.com.acaipaidegua.service.dto.EstabelecimentoDTO;
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
 * REST controller for managing Estabelecimento.
 */
@RestController
@RequestMapping("/api")
public class EstabelecimentoResource {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoResource.class);

    private static final String ENTITY_NAME = "estabelecimento";

    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoResource(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    /**
     * POST  /estabelecimentos : Create a new estabelecimento.
     *
     * @param estabelecimentoDTO the estabelecimentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estabelecimentoDTO, or with status 400 (Bad Request) if the estabelecimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estabelecimentos")
    public ResponseEntity<EstabelecimentoDTO> createEstabelecimento(@Valid @RequestBody EstabelecimentoDTO estabelecimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Estabelecimento : {}", estabelecimentoDTO);
        if (estabelecimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estabelecimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstabelecimentoDTO result = estabelecimentoService.save(estabelecimentoDTO);
        return ResponseEntity.created(new URI("/api/estabelecimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estabelecimentos : Updates an existing estabelecimento.
     *
     * @param estabelecimentoDTO the estabelecimentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estabelecimentoDTO,
     * or with status 400 (Bad Request) if the estabelecimentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the estabelecimentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estabelecimentos")
    public ResponseEntity<EstabelecimentoDTO> updateEstabelecimento(@Valid @RequestBody EstabelecimentoDTO estabelecimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Estabelecimento : {}", estabelecimentoDTO);
        if (estabelecimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstabelecimentoDTO result = estabelecimentoService.save(estabelecimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estabelecimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estabelecimentos : get all the estabelecimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estabelecimentos in body
     */
    @GetMapping("/estabelecimentos")
    public ResponseEntity<List<EstabelecimentoDTO>> getAllEstabelecimentos(Pageable pageable) {
        log.debug("REST request to get a page of Estabelecimentos");
        Page<EstabelecimentoDTO> page = estabelecimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estabelecimentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estabelecimentos/:id : get the "id" estabelecimento.
     *
     * @param id the id of the estabelecimentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estabelecimentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estabelecimentos/{id}")
    public ResponseEntity<EstabelecimentoDTO> getEstabelecimento(@PathVariable Long id) {
        log.debug("REST request to get Estabelecimento : {}", id);
        Optional<EstabelecimentoDTO> estabelecimentoDTO = estabelecimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estabelecimentoDTO);
    }

    /**
     * DELETE  /estabelecimentos/:id : delete the "id" estabelecimento.
     *
     * @param id the id of the estabelecimentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estabelecimentos/{id}")
    public ResponseEntity<Void> deleteEstabelecimento(@PathVariable Long id) {
        log.debug("REST request to delete Estabelecimento : {}", id);
        estabelecimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
