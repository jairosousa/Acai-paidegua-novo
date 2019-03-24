package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.CaracteristicaService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.CaracteristicaDTO;
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
 * REST controller for managing Caracteristica.
 */
@RestController
@RequestMapping("/api")
public class CaracteristicaResource {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaResource.class);

    private static final String ENTITY_NAME = "caracteristica";

    private final CaracteristicaService caracteristicaService;

    public CaracteristicaResource(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    /**
     * POST  /caracteristicas : Create a new caracteristica.
     *
     * @param caracteristicaDTO the caracteristicaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caracteristicaDTO, or with status 400 (Bad Request) if the caracteristica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caracteristicas")
    public ResponseEntity<CaracteristicaDTO> createCaracteristica(@Valid @RequestBody CaracteristicaDTO caracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to save Caracteristica : {}", caracteristicaDTO);
        if (caracteristicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new caracteristica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaracteristicaDTO result = caracteristicaService.save(caracteristicaDTO);
        return ResponseEntity.created(new URI("/api/caracteristicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caracteristicas : Updates an existing caracteristica.
     *
     * @param caracteristicaDTO the caracteristicaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caracteristicaDTO,
     * or with status 400 (Bad Request) if the caracteristicaDTO is not valid,
     * or with status 500 (Internal Server Error) if the caracteristicaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caracteristicas")
    public ResponseEntity<CaracteristicaDTO> updateCaracteristica(@Valid @RequestBody CaracteristicaDTO caracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to update Caracteristica : {}", caracteristicaDTO);
        if (caracteristicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaracteristicaDTO result = caracteristicaService.save(caracteristicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caracteristicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caracteristicas : get all the caracteristicas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caracteristicas in body
     */
    @GetMapping("/caracteristicas")
    public List<CaracteristicaDTO> getAllCaracteristicas() {
        log.debug("REST request to get all Caracteristicas");
        return caracteristicaService.findAll();
    }

    /**
     * GET  /caracteristicas/:id : get the "id" caracteristica.
     *
     * @param id the id of the caracteristicaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caracteristicaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/caracteristicas/{id}")
    public ResponseEntity<CaracteristicaDTO> getCaracteristica(@PathVariable Long id) {
        log.debug("REST request to get Caracteristica : {}", id);
        Optional<CaracteristicaDTO> caracteristicaDTO = caracteristicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caracteristicaDTO);
    }

    /**
     * DELETE  /caracteristicas/:id : delete the "id" caracteristica.
     *
     * @param id the id of the caracteristicaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caracteristicas/{id}")
    public ResponseEntity<Void> deleteCaracteristica(@PathVariable Long id) {
        log.debug("REST request to delete Caracteristica : {}", id);
        caracteristicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
