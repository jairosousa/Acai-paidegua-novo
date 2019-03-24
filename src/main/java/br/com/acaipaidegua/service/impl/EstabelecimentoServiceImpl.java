package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.EstabelecimentoService;
import br.com.acaipaidegua.domain.Estabelecimento;
import br.com.acaipaidegua.repository.EstabelecimentoRepository;
import br.com.acaipaidegua.service.dto.EstabelecimentoDTO;
import br.com.acaipaidegua.service.mapper.EstabelecimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Estabelecimento.
 */
@Service
@Transactional
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoServiceImpl.class);

    private final EstabelecimentoRepository estabelecimentoRepository;

    private final EstabelecimentoMapper estabelecimentoMapper;

    public EstabelecimentoServiceImpl(EstabelecimentoRepository estabelecimentoRepository, EstabelecimentoMapper estabelecimentoMapper) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.estabelecimentoMapper = estabelecimentoMapper;
    }

    /**
     * Save a estabelecimento.
     *
     * @param estabelecimentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstabelecimentoDTO save(EstabelecimentoDTO estabelecimentoDTO) {
        log.debug("Request to save Estabelecimento : {}", estabelecimentoDTO);
        Estabelecimento estabelecimento = estabelecimentoMapper.toEntity(estabelecimentoDTO);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
        return estabelecimentoMapper.toDto(estabelecimento);
    }

    /**
     * Get all the estabelecimentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstabelecimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estabelecimentos");
        return estabelecimentoRepository.findAll(pageable)
            .map(estabelecimentoMapper::toDto);
    }


    /**
     * Get one estabelecimento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstabelecimentoDTO> findOne(Long id) {
        log.debug("Request to get Estabelecimento : {}", id);
        return estabelecimentoRepository.findById(id)
            .map(estabelecimentoMapper::toDto);
    }

    /**
     * Delete the estabelecimento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estabelecimento : {}", id);
        estabelecimentoRepository.deleteById(id);
    }
}
