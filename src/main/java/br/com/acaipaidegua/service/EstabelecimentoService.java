package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.EstabelecimentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Estabelecimento.
 */
public interface EstabelecimentoService {

    /**
     * Save a estabelecimento.
     *
     * @param estabelecimentoDTO the entity to save
     * @return the persisted entity
     */
    EstabelecimentoDTO save(EstabelecimentoDTO estabelecimentoDTO);

    /**
     * Get all the estabelecimentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstabelecimentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estabelecimento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EstabelecimentoDTO> findOne(Long id);

    /**
     * Delete the "id" estabelecimento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
