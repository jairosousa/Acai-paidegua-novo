package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.HorarioFuncionamentoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.HorarioFuncionamentoDTO;
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
 * REST controller for managing HorarioFuncionamento.
 */
@RestController
@RequestMapping("/api")
public class HorarioFuncionamentoResource {

    private final Logger log = LoggerFactory.getLogger(HorarioFuncionamentoResource.class);

    private static final String ENTITY_NAME = "horarioFuncionamento";

    private final HorarioFuncionamentoService horarioFuncionamentoService;

    public HorarioFuncionamentoResource(HorarioFuncionamentoService horarioFuncionamentoService) {
        this.horarioFuncionamentoService = horarioFuncionamentoService;
    }

    /**
     * POST  /horario-funcionamentos : Create a new horarioFuncionamento.
     *
     * @param horarioFuncionamentoDTO the horarioFuncionamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horarioFuncionamentoDTO, or with status 400 (Bad Request) if the horarioFuncionamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horario-funcionamentos")
    public ResponseEntity<HorarioFuncionamentoDTO> createHorarioFuncionamento(@Valid @RequestBody HorarioFuncionamentoDTO horarioFuncionamentoDTO) throws URISyntaxException {
        log.debug("REST request to save HorarioFuncionamento : {}", horarioFuncionamentoDTO);
        if (horarioFuncionamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new horarioFuncionamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioFuncionamentoDTO result = horarioFuncionamentoService.save(horarioFuncionamentoDTO);
        return ResponseEntity.created(new URI("/api/horario-funcionamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horario-funcionamentos : Updates an existing horarioFuncionamento.
     *
     * @param horarioFuncionamentoDTO the horarioFuncionamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horarioFuncionamentoDTO,
     * or with status 400 (Bad Request) if the horarioFuncionamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the horarioFuncionamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horario-funcionamentos")
    public ResponseEntity<HorarioFuncionamentoDTO> updateHorarioFuncionamento(@Valid @RequestBody HorarioFuncionamentoDTO horarioFuncionamentoDTO) throws URISyntaxException {
        log.debug("REST request to update HorarioFuncionamento : {}", horarioFuncionamentoDTO);
        if (horarioFuncionamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioFuncionamentoDTO result = horarioFuncionamentoService.save(horarioFuncionamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, horarioFuncionamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horario-funcionamentos : get all the horarioFuncionamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of horarioFuncionamentos in body
     */
    @GetMapping("/horario-funcionamentos")
    public List<HorarioFuncionamentoDTO> getAllHorarioFuncionamentos() {
        log.debug("REST request to get all HorarioFuncionamentos");
        return horarioFuncionamentoService.findAll();
    }

    /**
     * GET  /horario-funcionamentos/:id : get the "id" horarioFuncionamento.
     *
     * @param id the id of the horarioFuncionamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horarioFuncionamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/horario-funcionamentos/{id}")
    public ResponseEntity<HorarioFuncionamentoDTO> getHorarioFuncionamento(@PathVariable Long id) {
        log.debug("REST request to get HorarioFuncionamento : {}", id);
        Optional<HorarioFuncionamentoDTO> horarioFuncionamentoDTO = horarioFuncionamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioFuncionamentoDTO);
    }

    /**
     * DELETE  /horario-funcionamentos/:id : delete the "id" horarioFuncionamento.
     *
     * @param id the id of the horarioFuncionamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horario-funcionamentos/{id}")
    public ResponseEntity<Void> deleteHorarioFuncionamento(@PathVariable Long id) {
        log.debug("REST request to delete HorarioFuncionamento : {}", id);
        horarioFuncionamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
