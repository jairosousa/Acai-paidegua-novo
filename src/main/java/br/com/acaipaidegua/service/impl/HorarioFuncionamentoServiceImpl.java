package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.HorarioFuncionamentoService;
import br.com.acaipaidegua.domain.HorarioFuncionamento;
import br.com.acaipaidegua.repository.HorarioFuncionamentoRepository;
import br.com.acaipaidegua.service.dto.HorarioFuncionamentoDTO;
import br.com.acaipaidegua.service.mapper.HorarioFuncionamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HorarioFuncionamento.
 */
@Service
@Transactional
public class HorarioFuncionamentoServiceImpl implements HorarioFuncionamentoService {

    private final Logger log = LoggerFactory.getLogger(HorarioFuncionamentoServiceImpl.class);

    private final HorarioFuncionamentoRepository horarioFuncionamentoRepository;

    private final HorarioFuncionamentoMapper horarioFuncionamentoMapper;

    public HorarioFuncionamentoServiceImpl(HorarioFuncionamentoRepository horarioFuncionamentoRepository, HorarioFuncionamentoMapper horarioFuncionamentoMapper) {
        this.horarioFuncionamentoRepository = horarioFuncionamentoRepository;
        this.horarioFuncionamentoMapper = horarioFuncionamentoMapper;
    }

    /**
     * Save a horarioFuncionamento.
     *
     * @param horarioFuncionamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HorarioFuncionamentoDTO save(HorarioFuncionamentoDTO horarioFuncionamentoDTO) {
        log.debug("Request to save HorarioFuncionamento : {}", horarioFuncionamentoDTO);
        HorarioFuncionamento horarioFuncionamento = horarioFuncionamentoMapper.toEntity(horarioFuncionamentoDTO);
        horarioFuncionamento = horarioFuncionamentoRepository.save(horarioFuncionamento);
        return horarioFuncionamentoMapper.toDto(horarioFuncionamento);
    }

    /**
     * Get all the horarioFuncionamentos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HorarioFuncionamentoDTO> findAll() {
        log.debug("Request to get all HorarioFuncionamentos");
        return horarioFuncionamentoRepository.findAll().stream()
            .map(horarioFuncionamentoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one horarioFuncionamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HorarioFuncionamentoDTO> findOne(Long id) {
        log.debug("Request to get HorarioFuncionamento : {}", id);
        return horarioFuncionamentoRepository.findById(id)
            .map(horarioFuncionamentoMapper::toDto);
    }

    /**
     * Delete the horarioFuncionamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HorarioFuncionamento : {}", id);
        horarioFuncionamentoRepository.deleteById(id);
    }
}
