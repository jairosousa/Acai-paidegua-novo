package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.ResiduoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Residuo.
 */
public interface ResiduoService {

    /**
     * Save a residuo.
     *
     * @param residuoDTO the entity to save
     * @return the persisted entity
     */
    ResiduoDTO save(ResiduoDTO residuoDTO);

    /**
     * Get all the residuos.
     *
     * @return the list of entities
     */
    List<ResiduoDTO> findAll();


    /**
     * Get the "id" residuo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResiduoDTO> findOne(Long id);

    /**
     * Delete the "id" residuo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
