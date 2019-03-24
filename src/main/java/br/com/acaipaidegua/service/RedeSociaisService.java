package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.RedeSociaisDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RedeSociais.
 */
public interface RedeSociaisService {

    /**
     * Save a redeSociais.
     *
     * @param redeSociaisDTO the entity to save
     * @return the persisted entity
     */
    RedeSociaisDTO save(RedeSociaisDTO redeSociaisDTO);

    /**
     * Get all the redeSociais.
     *
     * @return the list of entities
     */
    List<RedeSociaisDTO> findAll();


    /**
     * Get the "id" redeSociais.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RedeSociaisDTO> findOne(Long id);

    /**
     * Delete the "id" redeSociais.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
