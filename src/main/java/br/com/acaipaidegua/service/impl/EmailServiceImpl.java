package br.com.acaipaidegua.service.impl;

import br.com.acaipaidegua.service.EmailService;
import br.com.acaipaidegua.domain.Email;
import br.com.acaipaidegua.repository.EmailRepository;
import br.com.acaipaidegua.service.dto.EmailDTO;
import br.com.acaipaidegua.service.mapper.EmailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Email.
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailServiceImpl(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailDTO save(EmailDTO emailDTO) {
        log.debug("Request to save Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Get all the emails.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailDTO> findAll() {
        log.debug("Request to get all Emails");
        return emailRepository.findAll().stream()
            .map(emailMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one email by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailDTO> findOne(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findById(id)
            .map(emailMapper::toDto);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
    }
}
