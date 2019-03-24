package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.BeneficiamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Beneficiamento and its DTO BeneficiamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {ResiduoMapper.class})
public interface BeneficiamentoMapper extends EntityMapper<BeneficiamentoDTO, Beneficiamento> {

    @Mapping(source = "residuo.id", target = "residuoId")
    BeneficiamentoDTO toDto(Beneficiamento beneficiamento);

    @Mapping(source = "residuoId", target = "residuo")
    Beneficiamento toEntity(BeneficiamentoDTO beneficiamentoDTO);

    default Beneficiamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Beneficiamento beneficiamento = new Beneficiamento();
        beneficiamento.setId(id);
        return beneficiamento;
    }
}
