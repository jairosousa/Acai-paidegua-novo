package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.CaracteristicaService;
import br.com.acaipaidegua.domain.Caracteristica;
import br.com.acaipaidegua.repository.CaracteristicaRepository;
import br.com.acaipaidegua.service.dto.CaracteristicaDTO;
import br.com.acaipaidegua.service.mapper.CaracteristicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Caracteristica.
 */
@Service
@Transactional
public class CaracteristicaServiceImpl implements CaracteristicaService {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaServiceImpl.class);

    private final CaracteristicaRepository caracteristicaRepository;

    private final CaracteristicaMapper caracteristicaMapper;

    public CaracteristicaServiceImpl(CaracteristicaRepository caracteristicaRepository, CaracteristicaMapper caracteristicaMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.caracteristicaMapper = caracteristicaMapper;
    }

    /**
     * Save a caracteristica.
     *
     * @param caracteristicaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CaracteristicaDTO save(CaracteristicaDTO caracteristicaDTO) {
        log.debug("Request to save Caracteristica : {}", caracteristicaDTO);
        Caracteristica caracteristica = caracteristicaMapper.toEntity(caracteristicaDTO);
        caracteristica = caracteristicaRepository.save(caracteristica);
        return caracteristicaMapper.toDto(caracteristica);
    }

    /**
     * Get all the caracteristicas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CaracteristicaDTO> findAll() {
        log.debug("Request to get all Caracteristicas");
        return caracteristicaRepository.findAll().stream()
            .map(caracteristicaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one caracteristica by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CaracteristicaDTO> findOne(Long id) {
        log.debug("Request to get Caracteristica : {}", id);
        return caracteristicaRepository.findById(id)
            .map(caracteristicaMapper::toDto);
    }

    /**
     * Delete the caracteristica by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caracteristica : {}", id);
        caracteristicaRepository.deleteById(id);
    }
}
