package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.ImagemService;
import br.com.acaipaidegua.domain.Imagem;
import br.com.acaipaidegua.repository.ImagemRepository;
import br.com.acaipaidegua.service.dto.ImagemDTO;
import br.com.acaipaidegua.service.mapper.ImagemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Imagem.
 */
@Service
@Transactional
public class ImagemServiceImpl implements ImagemService {

    private final Logger log = LoggerFactory.getLogger(ImagemServiceImpl.class);

    private final ImagemRepository imagemRepository;

    private final ImagemMapper imagemMapper;

    public ImagemServiceImpl(ImagemRepository imagemRepository, ImagemMapper imagemMapper) {
        this.imagemRepository = imagemRepository;
        this.imagemMapper = imagemMapper;
    }

    /**
     * Save a imagem.
     *
     * @param imagemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ImagemDTO save(ImagemDTO imagemDTO) {
        log.debug("Request to save Imagem : {}", imagemDTO);
        Imagem imagem = imagemMapper.toEntity(imagemDTO);
        imagem = imagemRepository.save(imagem);
        return imagemMapper.toDto(imagem);
    }

    /**
     * Get all the imagems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImagemDTO> findAll() {
        log.debug("Request to get all Imagems");
        return imagemRepository.findAll().stream()
            .map(imagemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one imagem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImagemDTO> findOne(Long id) {
        log.debug("Request to get Imagem : {}", id);
        return imagemRepository.findById(id)
            .map(imagemMapper::toDto);
    }

    /**
     * Delete the imagem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Imagem : {}", id);
        imagemRepository.deleteById(id);
    }
}
