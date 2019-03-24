package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.GeolocalizacaoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Geolocalizacao.
 */
public interface GeolocalizacaoService {

    /**
     * Save a geolocalizacao.
     *
     * @param geolocalizacaoDTO the entity to save
     * @return the persisted entity
     */
    GeolocalizacaoDTO save(GeolocalizacaoDTO geolocalizacaoDTO);

    /**
     * Get all the geolocalizacaos.
     *
     * @return the list of entities
     */
    List<GeolocalizacaoDTO> findAll();


    /**
     * Get the "id" geolocalizacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GeolocalizacaoDTO> findOne(Long id);

    /**
     * Delete the "id" geolocalizacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
