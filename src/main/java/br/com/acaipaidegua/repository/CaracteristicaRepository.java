package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.Caracteristica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Caracteristica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {

}
