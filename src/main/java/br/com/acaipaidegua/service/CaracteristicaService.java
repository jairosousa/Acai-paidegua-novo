package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.CaracteristicaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Caracteristica.
 */
public interface CaracteristicaService {

    /**
     * Save a caracteristica.
     *
     * @param caracteristicaDTO the entity to save
     * @return the persisted entity
     */
    CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO);

    /**
     * Get all the caracteristicas.
     *
     * @return the list of entities
     */
    List<CaracteristicaDTO> findAll();


    /**
     * Get the "id" caracteristica.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CaracteristicaDTO> findOne(Long id);

    /**
     * Delete the "id" caracteristica.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
