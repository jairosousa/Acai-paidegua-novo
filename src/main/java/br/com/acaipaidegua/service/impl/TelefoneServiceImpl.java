package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.TelefoneService;
import br.com.acaipaidegua.domain.Telefone;
import br.com.acaipaidegua.repository.TelefoneRepository;
import br.com.acaipaidegua.service.dto.TelefoneDTO;
import br.com.acaipaidegua.service.mapper.TelefoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Telefone.
 */
@Service
@Transactional
public class TelefoneServiceImpl implements TelefoneService {

    private final Logger log = LoggerFactory.getLogger(TelefoneServiceImpl.class);

    private final TelefoneRepository telefoneRepository;

    private final TelefoneMapper telefoneMapper;

    public TelefoneServiceImpl(TelefoneRepository telefoneRepository, TelefoneMapper telefoneMapper) {
        this.telefoneRepository = telefoneRepository;
        this.telefoneMapper = telefoneMapper;
    }

    /**
     * Save a telefone.
     *
     * @param telefoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TelefoneDTO save(TelefoneDTO telefoneDTO) {
        log.debug("Request to save Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        return telefoneMapper.toDto(telefone);
    }

    /**
     * Get all the telefones.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TelefoneDTO> findAll() {
        log.debug("Request to get all Telefones");
        return telefoneRepository.findAll().stream()
            .map(telefoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one telefone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TelefoneDTO> findOne(Long id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findById(id)
            .map(telefoneMapper::toDto);
    }

    /**
     * Delete the telefone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
    }
}
