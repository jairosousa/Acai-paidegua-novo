package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.RedeSociaisService;
import br.com.acaipaidegua.domain.RedeSociais;
import br.com.acaipaidegua.repository.RedeSociaisRepository;
import br.com.acaipaidegua.service.dto.RedeSociaisDTO;
import br.com.acaipaidegua.service.mapper.RedeSociaisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RedeSociais.
 */
@Service
@Transactional
public class RedeSociaisServiceImpl implements RedeSociaisService {

    private final Logger log = LoggerFactory.getLogger(RedeSociaisServiceImpl.class);

    private final RedeSociaisRepository redeSociaisRepository;

    private final RedeSociaisMapper redeSociaisMapper;

    public RedeSociaisServiceImpl(RedeSociaisRepository redeSociaisRepository, RedeSociaisMapper redeSociaisMapper) {
        this.redeSociaisRepository = redeSociaisRepository;
        this.redeSociaisMapper = redeSociaisMapper;
    }

    /**
     * Save a redeSociais.
     *
     * @param redeSociaisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RedeSociaisDTO save(RedeSociaisDTO redeSociaisDTO) {
        log.debug("Request to save RedeSociais : {}", redeSociaisDTO);
        RedeSociais redeSociais = redeSociaisMapper.toEntity(redeSociaisDTO);
        redeSociais = redeSociaisRepository.save(redeSociais);
        return redeSociaisMapper.toDto(redeSociais);
    }

    /**
     * Get all the redeSociais.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RedeSociaisDTO> findAll() {
        log.debug("Request to get all RedeSociais");
        return redeSociaisRepository.findAll().stream()
            .map(redeSociaisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one redeSociais by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RedeSociaisDTO> findOne(Long id) {
        log.debug("Request to get RedeSociais : {}", id);
        return redeSociaisRepository.findById(id)
            .map(redeSociaisMapper::toDto);
    }

    /**
     * Delete the redeSociais by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RedeSociais : {}", id);
        redeSociaisRepository.deleteById(id);
    }
}
