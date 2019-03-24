package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.HorarioFuncionamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HorarioFuncionamento and its DTO HorarioFuncionamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {EstabelecimentoMapper.class})
public interface HorarioFuncionamentoMapper extends EntityMapper<HorarioFuncionamentoDTO, HorarioFuncionamento> {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    HorarioFuncionamentoDTO toDto(HorarioFuncionamento horarioFuncionamento);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento")
    HorarioFuncionamento toEntity(HorarioFuncionamentoDTO horarioFuncionamentoDTO);

    default HorarioFuncionamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        HorarioFuncionamento horarioFuncionamento = new HorarioFuncionamento();
        horarioFuncionamento.setId(id);
        return horarioFuncionamento;
    }
}
