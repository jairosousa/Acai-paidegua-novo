package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.HorarioFuncionamentoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing HorarioFuncionamento.
 */
public interface HorarioFuncionamentoService {

    /**
     * Save a horarioFuncionamento.
     *
     * @param horarioFuncionamentoDTO the entity to save
     * @return the persisted entity
     */
    HorarioFuncionamentoDTO save(HorarioFuncionamentoDTO horarioFuncionamentoDTO);

    /**
     * Get all the horarioFuncionamentos.
     *
     * @return the list of entities
     */
    List<HorarioFuncionamentoDTO> findAll();


    /**
     * Get the "id" horarioFuncionamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HorarioFuncionamentoDTO> findOne(Long id);

    /**
     * Delete the "id" horarioFuncionamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
