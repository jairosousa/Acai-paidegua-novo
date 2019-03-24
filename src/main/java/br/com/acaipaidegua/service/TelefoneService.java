package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.TelefoneDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Telefone.
 */
public interface TelefoneService {

    /**
     * Save a telefone.
     *
     * @param telefoneDTO the entity to save
     * @return the persisted entity
     */
    TelefoneDTO save(TelefoneDTO telefoneDTO);

    /**
     * Get all the telefones.
     *
     * @return the list of entities
     */
    List<TelefoneDTO> findAll();


    /**
     * Get the "id" telefone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TelefoneDTO> findOne(Long id);

    /**
     * Delete the "id" telefone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
