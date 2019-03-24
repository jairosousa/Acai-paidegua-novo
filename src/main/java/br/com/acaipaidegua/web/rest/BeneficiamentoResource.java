package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.BeneficiamentoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.web.rest.util.PaginationUtil;
import br.com.acaipaidegua.service.dto.BeneficiamentoDTO;
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
 * REST controller for managing Beneficiamento.
 */
@RestController
@RequestMapping("/api")
public class BeneficiamentoResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiamentoResource.class);

    private static final String ENTITY_NAME = "beneficiamento";

    private final BeneficiamentoService beneficiamentoService;

    public BeneficiamentoResource(BeneficiamentoService beneficiamentoService) {
        this.beneficiamentoService = beneficiamentoService;
    }

    /**
     * POST  /beneficiamentos : Create a new beneficiamento.
     *
     * @param beneficiamentoDTO the beneficiamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beneficiamentoDTO, or with status 400 (Bad Request) if the beneficiamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beneficiamentos")
    public ResponseEntity<BeneficiamentoDTO> createBeneficiamento(@Valid @RequestBody BeneficiamentoDTO beneficiamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Beneficiamento : {}", beneficiamentoDTO);
        if (beneficiamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new beneficiamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiamentoDTO result = beneficiamentoService.save(beneficiamentoDTO);
        return ResponseEntity.created(new URI("/api/beneficiamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beneficiamentos : Updates an existing beneficiamento.
     *
     * @param beneficiamentoDTO the beneficiamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beneficiamentoDTO,
     * or with status 400 (Bad Request) if the beneficiamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the beneficiamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beneficiamentos")
    public ResponseEntity<BeneficiamentoDTO> updateBeneficiamento(@Valid @RequestBody BeneficiamentoDTO beneficiamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Beneficiamento : {}", beneficiamentoDTO);
        if (beneficiamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeneficiamentoDTO result = beneficiamentoService.save(beneficiamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beneficiamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beneficiamentos : get all the beneficiamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of beneficiamentos in body
     */
    @GetMapping("/beneficiamentos")
    public ResponseEntity<List<BeneficiamentoDTO>> getAllBeneficiamentos(Pageable pageable) {
        log.debug("REST request to get a page of Beneficiamentos");
        Page<BeneficiamentoDTO> page = beneficiamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/beneficiamentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /beneficiamentos/:id : get the "id" beneficiamento.
     *
     * @param id the id of the beneficiamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beneficiamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/beneficiamentos/{id}")
    public ResponseEntity<BeneficiamentoDTO> getBeneficiamento(@PathVariable Long id) {
        log.debug("REST request to get Beneficiamento : {}", id);
        Optional<BeneficiamentoDTO> beneficiamentoDTO = beneficiamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beneficiamentoDTO);
    }

    /**
     * DELETE  /beneficiamentos/:id : delete the "id" beneficiamento.
     *
     * @param id the id of the beneficiamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beneficiamentos/{id}")
    public ResponseEntity<Void> deleteBeneficiamento(@PathVariable Long id) {
        log.debug("REST request to delete Beneficiamento : {}", id);
        beneficiamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
