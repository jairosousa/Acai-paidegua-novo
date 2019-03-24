package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.ResiduoService;
import br.com.acaipaidegua.domain.Residuo;
import br.com.acaipaidegua.repository.ResiduoRepository;
import br.com.acaipaidegua.service.dto.ResiduoDTO;
import br.com.acaipaidegua.service.mapper.ResiduoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Residuo.
 */
@Service
@Transactional
public class ResiduoServiceImpl implements ResiduoService {

    private final Logger log = LoggerFactory.getLogger(ResiduoServiceImpl.class);

    private final ResiduoRepository residuoRepository;

    private final ResiduoMapper residuoMapper;

    public ResiduoServiceImpl(ResiduoRepository residuoRepository, ResiduoMapper residuoMapper) {
        this.residuoRepository = residuoRepository;
        this.residuoMapper = residuoMapper;
    }

    /**
     * Save a residuo.
     *
     * @param residuoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResiduoDTO save(ResiduoDTO residuoDTO) {
        log.debug("Request to save Residuo : {}", residuoDTO);
        Residuo residuo = residuoMapper.toEntity(residuoDTO);
        residuo = residuoRepository.save(residuo);
        return residuoMapper.toDto(residuo);
    }

    /**
     * Get all the residuos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResiduoDTO> findAll() {
        log.debug("Request to get all Residuos");
        return residuoRepository.findAll().stream()
            .map(residuoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one residuo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResiduoDTO> findOne(Long id) {
        log.debug("Request to get Residuo : {}", id);
        return residuoRepository.findById(id)
            .map(residuoMapper::toDto);
    }

    /**
     * Delete the residuo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Residuo : {}", id);
        residuoRepository.deleteById(id);
    }
}
