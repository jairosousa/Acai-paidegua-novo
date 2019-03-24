package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.TabelaPrecoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TabelaPreco.
 */
public interface TabelaPrecoService {

    /**
     * Save a tabelaPreco.
     *
     * @param tabelaPrecoDTO the entity to save
     * @return the persisted entity
     */
    TabelaPrecoDTO save(TabelaPrecoDTO tabelaPrecoDTO);

    /**
     * Get all the tabelaPrecos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TabelaPrecoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tabelaPreco.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TabelaPrecoDTO> findOne(Long id);

    /**
     * Delete the "id" tabelaPreco.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
