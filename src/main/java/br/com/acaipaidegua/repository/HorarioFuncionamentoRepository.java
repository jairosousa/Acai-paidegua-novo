package br.com.acaipaidegua.repository;

import br.com.acaipaidegua.domain.HorarioFuncionamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HorarioFuncionamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Long> {

}
