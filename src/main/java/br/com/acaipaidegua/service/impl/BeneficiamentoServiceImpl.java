package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.BeneficiamentoService;
import br.com.acaipaidegua.domain.Beneficiamento;
import br.com.acaipaidegua.repository.BeneficiamentoRepository;
import br.com.acaipaidegua.service.dto.BeneficiamentoDTO;
import br.com.acaipaidegua.service.mapper.BeneficiamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Beneficiamento.
 */
@Service
@Transactional
public class BeneficiamentoServiceImpl implements BeneficiamentoService {

    private final Logger log = LoggerFactory.getLogger(BeneficiamentoServiceImpl.class);

    private final BeneficiamentoRepository beneficiamentoRepository;

    private final BeneficiamentoMapper beneficiamentoMapper;

    public BeneficiamentoServiceImpl(BeneficiamentoRepository beneficiamentoRepository, BeneficiamentoMapper beneficiamentoMapper) {
        this.beneficiamentoRepository = beneficiamentoRepository;
        this.beneficiamentoMapper = beneficiamentoMapper;
    }

    /**
     * Save a beneficiamento.
     *
     * @param beneficiamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BeneficiamentoDTO save(BeneficiamentoDTO beneficiamentoDTO) {
        log.debug("Request to save Beneficiamento : {}", beneficiamentoDTO);
        Beneficiamento beneficiamento = beneficiamentoMapper.toEntity(beneficiamentoDTO);
        beneficiamento = beneficiamentoRepository.save(beneficiamento);
        return beneficiamentoMapper.toDto(beneficiamento);
    }

    /**
     * Get all the beneficiamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BeneficiamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beneficiamentos");
        return beneficiamentoRepository.findAll(pageable)
            .map(beneficiamentoMapper::toDto);
    }


    /**
     * Get one beneficiamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BeneficiamentoDTO> findOne(Long id) {
        log.debug("Request to get Beneficiamento : {}", id);
        return beneficiamentoRepository.findById(id)
            .map(beneficiamentoMapper::toDto);
    }

    /**
     * Delete the beneficiamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Beneficiamento : {}", id);
        beneficiamentoRepository.deleteById(id);
    }
}
