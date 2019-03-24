package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.GeolocalizacaoService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.GeolocalizacaoDTO;
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
 * REST controller for managing Geolocalizacao.
 */
@RestController
@RequestMapping("/api")
public class GeolocalizacaoResource {

    private final Logger log = LoggerFactory.getLogger(GeolocalizacaoResource.class);

    private static final String ENTITY_NAME = "geolocalizacao";

    private final GeolocalizacaoService geolocalizacaoService;

    public GeolocalizacaoResource(GeolocalizacaoService geolocalizacaoService) {
        this.geolocalizacaoService = geolocalizacaoService;
    }

    /**
     * POST  /geolocalizacaos : Create a new geolocalizacao.
     *
     * @param geolocalizacaoDTO the geolocalizacaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geolocalizacaoDTO, or with status 400 (Bad Request) if the geolocalizacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/geolocalizacaos")
    public ResponseEntity<GeolocalizacaoDTO> createGeolocalizacao(@Valid @RequestBody GeolocalizacaoDTO geolocalizacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Geolocalizacao : {}", geolocalizacaoDTO);
        if (geolocalizacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new geolocalizacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeolocalizacaoDTO result = geolocalizacaoService.save(geolocalizacaoDTO);
        return ResponseEntity.created(new URI("/api/geolocalizacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /geolocalizacaos : Updates an existing geolocalizacao.
     *
     * @param geolocalizacaoDTO the geolocalizacaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geolocalizacaoDTO,
     * or with status 400 (Bad Request) if the geolocalizacaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the geolocalizacaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/geolocalizacaos")
    public ResponseEntity<GeolocalizacaoDTO> updateGeolocalizacao(@Valid @RequestBody GeolocalizacaoDTO geolocalizacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Geolocalizacao : {}", geolocalizacaoDTO);
        if (geolocalizacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeolocalizacaoDTO result = geolocalizacaoService.save(geolocalizacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, geolocalizacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /geolocalizacaos : get all the geolocalizacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of geolocalizacaos in body
     */
    @GetMapping("/geolocalizacaos")
    public List<GeolocalizacaoDTO> getAllGeolocalizacaos() {
        log.debug("REST request to get all Geolocalizacaos");
        return geolocalizacaoService.findAll();
    }

    /**
     * GET  /geolocalizacaos/:id : get the "id" geolocalizacao.
     *
     * @param id the id of the geolocalizacaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geolocalizacaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/geolocalizacaos/{id}")
    public ResponseEntity<GeolocalizacaoDTO> getGeolocalizacao(@PathVariable Long id) {
        log.debug("REST request to get Geolocalizacao : {}", id);
        Optional<GeolocalizacaoDTO> geolocalizacaoDTO = geolocalizacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geolocalizacaoDTO);
    }

    /**
     * DELETE  /geolocalizacaos/:id : delete the "id" geolocalizacao.
     *
     * @param id the id of the geolocalizacaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/geolocalizacaos/{id}")
    public ResponseEntity<Void> deleteGeolocalizacao(@PathVariable Long id) {
        log.debug("REST request to delete Geolocalizacao : {}", id);
        geolocalizacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
