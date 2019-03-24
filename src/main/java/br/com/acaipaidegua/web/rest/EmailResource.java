package br.com.acaipaidegua.web.rest;
import br.com.acaipaidegua.service.EmailService;
import br.com.acaipaidegua.web.rest.errors.BadRequestAlertException;
import br.com.acaipaidegua.web.rest.util.HeaderUtil;
import br.com.acaipaidegua.service.dto.EmailDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Email.
 */
@RestController
@RequestMapping("/api")
public class EmailResource {

    private final Logger log = LoggerFactory.getLogger(EmailResource.class);

    private static final String ENTITY_NAME = "email";

    private final EmailService emailService;

    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * POST  /emails : Create a new email.
     *
     * @param emailDTO the emailDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailDTO, or with status 400 (Bad Request) if the email has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emails")
    public ResponseEntity<EmailDTO> createEmail(@Valid @RequestBody EmailDTO emailDTO) throws URISyntaxException {
        log.debug("REST request to save Email : {}", emailDTO);
        if (emailDTO.getId() != null) {
            throw new BadRequestAlertException("A new email cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailDTO result = emailService.save(emailDTO);
        return ResponseEntity.created(new URI("/api/emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emails : Updates an existing email.
     *
     * @param emailDTO the emailDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailDTO,
     * or with status 400 (Bad Request) if the emailDTO is not valid,
     * or with status 500 (Internal Server Error) if the emailDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emails")
    public ResponseEntity<EmailDTO> updateEmail(@Valid @RequestBody EmailDTO emailDTO) throws URISyntaxException {
        log.debug("REST request to update Email : {}", emailDTO);
        if (emailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailDTO result = emailService.save(emailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emailDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emails : get all the emails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emails in body
     */
    @GetMapping("/emails")
    public List<EmailDTO> getAllEmails() {
        log.debug("REST request to get all Emails");
        return emailService.findAll();
    }

    /**
     * GET  /emails/:id : get the "id" email.
     *
     * @param id the id of the emailDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailDTO, or with status 404 (Not Found)
     */
    @GetMapping("/emails/{id}")
    public ResponseEntity<EmailDTO> getEmail(@PathVariable Long id) {
        log.debug("REST request to get Email : {}", id);
        Optional<EmailDTO> emailDTO = emailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailDTO);
    }

    /**
     * DELETE  /emails/:id : delete the "id" email.
     *
     * @param id the id of the emailDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emails/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        log.debug("REST request to delete Email : {}", id);
        emailService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
