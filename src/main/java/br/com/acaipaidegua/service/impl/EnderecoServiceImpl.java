package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.EnderecoService;
import br.com.acaipaidegua.domain.Endereco;
import br.com.acaipaidegua.repository.EnderecoRepository;
import br.com.acaipaidegua.service.dto.EnderecoDTO;
import br.com.acaipaidegua.service.mapper.EnderecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Endereco.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    /**
     * Get all the enderecos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findAll() {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll().stream()
            .map(enderecoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id)
            .map(enderecoMapper::toDto);
    }

    /**
     * Delete the endereco by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
