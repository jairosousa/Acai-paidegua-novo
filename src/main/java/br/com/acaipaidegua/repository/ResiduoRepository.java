package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.Residuo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Residuo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResiduoRepository extends JpaRepository<Residuo, Long> {

}
