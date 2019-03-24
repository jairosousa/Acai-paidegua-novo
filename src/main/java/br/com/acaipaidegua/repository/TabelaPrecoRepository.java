package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.TabelaPreco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TabelaPreco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TabelaPrecoRepository extends JpaRepository<TabelaPreco, Long> {

}
