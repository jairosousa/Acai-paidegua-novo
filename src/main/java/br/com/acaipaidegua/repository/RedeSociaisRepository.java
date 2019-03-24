package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.RedeSociais;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RedeSociais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedeSociaisRepository extends JpaRepository<RedeSociais, Long> {

}
