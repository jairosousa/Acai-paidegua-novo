package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.Email;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Email entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}
