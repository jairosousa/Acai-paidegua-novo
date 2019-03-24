package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.Imagem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Imagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {

}
