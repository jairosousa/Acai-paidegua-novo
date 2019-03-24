package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.TabelaPrecoService;
import br.com.acaipaidegua.domain.TabelaPreco;
import br.com.acaipaidegua.repository.TabelaPrecoRepository;
import br.com.acaipaidegua.service.dto.TabelaPrecoDTO;
import br.com.acaipaidegua.service.mapper.TabelaPrecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TabelaPreco.
 */
@Service
@Transactional
public class TabelaPrecoServiceImpl implements TabelaPrecoService {

    private final Logger log = LoggerFactory.getLogger(TabelaPrecoServiceImpl.class);

    private final TabelaPrecoRepository tabelaPrecoRepository;

    private final TabelaPrecoMapper tabelaPrecoMapper;

    public TabelaPrecoServiceImpl(TabelaPrecoRepository tabelaPrecoRepository, TabelaPrecoMapper tabelaPrecoMapper) {
        this.tabelaPrecoRepository = tabelaPrecoRepository;
        this.tabelaPrecoMapper = tabelaPrecoMapper;
    }

    /**
     * Save a tabelaPreco.
     *
     * @param tabelaPrecoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TabelaPrecoDTO save(TabelaPrecoDTO tabelaPrecoDTO) {
        log.debug("Request to save TabelaPreco : {}", tabelaPrecoDTO);
        TabelaPreco tabelaPreco = tabelaPrecoMapper.toEntity(tabelaPrecoDTO);
        tabelaPreco = tabelaPrecoRepository.save(tabelaPreco);
        return tabelaPrecoMapper.toDto(tabelaPreco);
    }

    /**
     * Get all the tabelaPrecos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TabelaPrecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TabelaPrecos");
        return tabelaPrecoRepository.findAll(pageable)
            .map(tabelaPrecoMapper::toDto);
    }


    /**
     * Get one tabelaPreco by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TabelaPrecoDTO> findOne(Long id) {
        log.debug("Request to get TabelaPreco : {}", id);
        return tabelaPrecoRepository.findById(id)
            .map(tabelaPrecoMapper::toDto);
    }

    /**
     * Delete the tabelaPreco by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TabelaPreco : {}", id);
        tabelaPrecoRepository.deleteById(id);
    }
}
