package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.GeolocalizacaoService;
import br.com.acaipaidegua.domain.Geolocalizacao;
import br.com.acaipaidegua.repository.GeolocalizacaoRepository;
import br.com.acaipaidegua.service.dto.GeolocalizacaoDTO;
import br.com.acaipaidegua.service.mapper.GeolocalizacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Geolocalizacao.
 */
@Service
@Transactional
public class GeolocalizacaoServiceImpl implements GeolocalizacaoService {

    private final Logger log = LoggerFactory.getLogger(GeolocalizacaoServiceImpl.class);

    private final GeolocalizacaoRepository geolocalizacaoRepository;

    private final GeolocalizacaoMapper geolocalizacaoMapper;

    public GeolocalizacaoServiceImpl(GeolocalizacaoRepository geolocalizacaoRepository, GeolocalizacaoMapper geolocalizacaoMapper) {
        this.geolocalizacaoRepository = geolocalizacaoRepository;
        this.geolocalizacaoMapper = geolocalizacaoMapper;
    }

    /**
     * Save a geolocalizacao.
     *
     * @param geolocalizacaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GeolocalizacaoDTO save(GeolocalizacaoDTO geolocalizacaoDTO) {
        log.debug("Request to save Geolocalizacao : {}", geolocalizacaoDTO);
        Geolocalizacao geolocalizacao = geolocalizacaoMapper.toEntity(geolocalizacaoDTO);
        geolocalizacao = geolocalizacaoRepository.save(geolocalizacao);
        return geolocalizacaoMapper.toDto(geolocalizacao);
    }

    /**
     * Get all the geolocalizacaos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GeolocalizacaoDTO> findAll() {
        log.debug("Request to get all Geolocalizacaos");
        return geolocalizacaoRepository.findAll().stream()
            .map(geolocalizacaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one geolocalizacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GeolocalizacaoDTO> findOne(Long id) {
        log.debug("Request to get Geolocalizacao : {}", id);
        return geolocalizacaoRepository.findById(id)
            .map(geolocalizacaoMapper::toDto);
    }

    /**
     * Delete the geolocalizacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Geolocalizacao : {}", id);
        geolocalizacaoRepository.deleteById(id);
    }
}
