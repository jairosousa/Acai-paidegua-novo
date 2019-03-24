package br.com.acaipaidegua.service;

import br.com.acaipaidegua.service.dto.EmailDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Email.
 */
public interface EmailService {

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save
     * @return the persisted entity
     */
    EmailDTO save(EmailDTO emailDTO);

    /**
     * Get all the emails.
     *
     * @return the list of entities
     */
    List<EmailDTO> findAll();


    /**
     * Get the "id" email.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmailDTO> findOne(Long id);

    /**
     * Delete the "id" email.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
