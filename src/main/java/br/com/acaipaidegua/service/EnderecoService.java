package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.EnderecoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Endereco.
 */
public interface EnderecoService {

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save
     * @return the persisted entity
     */
    EnderecoDTO save(EnderecoDTO enderecoDTO);

    /**
     * Get all the enderecos.
     *
     * @return the list of entities
     */
    List<EnderecoDTO> findAll();


    /**
     * Get the "id" endereco.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EnderecoDTO> findOne(Long id);

    /**
     * Delete the "id" endereco.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
