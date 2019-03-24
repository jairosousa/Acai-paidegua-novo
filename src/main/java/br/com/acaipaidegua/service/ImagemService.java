package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.ImagemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Imagem.
 */
public interface ImagemService {

    /**
     * Save a imagem.
     *
     * @param imagemDTO the entity to save
     * @return the persisted entity
     */
    ImagemDTO save(ImagemDTO imagemDTO);

    /**
     * Get all the imagems.
     *
     * @return the list of entities
     */
    List<ImagemDTO> findAll();


    /**
     * Get the "id" imagem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ImagemDTO> findOne(Long id);

    /**
     * Delete the "id" imagem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
