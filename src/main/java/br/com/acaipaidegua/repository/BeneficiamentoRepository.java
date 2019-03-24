package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.Beneficiamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Beneficiamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiamentoRepository extends JpaRepository<Beneficiamento, Long> {

}
