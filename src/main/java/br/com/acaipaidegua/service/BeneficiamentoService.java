package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.BeneficiamentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Beneficiamento.
 */
public interface BeneficiamentoService {

    /**
     * Save a beneficiamento.
     *
     * @param beneficiamentoDTO the entity to save
     * @return the persisted entity
     */
    BeneficiamentoDTO save(BeneficiamentoDTO beneficiamentoDTO);

    /**
     * Get all the beneficiamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BeneficiamentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" beneficiamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BeneficiamentoDTO> findOne(Long id);

    /**
     * Delete the "id" beneficiamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
