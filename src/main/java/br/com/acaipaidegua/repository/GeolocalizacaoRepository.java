package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.Geolocalizacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Geolocalizacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeolocalizacaoRepository extends JpaRepository<Geolocalizacao, Long> {

}
