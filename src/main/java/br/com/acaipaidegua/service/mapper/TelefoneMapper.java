package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.TelefoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Telefone and its DTO TelefoneDTO.
 */
@Mapper(componentModel = "spring", uses = {EstabelecimentoMapper.class})
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    TelefoneDTO toDto(Telefone telefone);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento")
    Telefone toEntity(TelefoneDTO telefoneDTO);

    default Telefone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telefone telefone = new Telefone();
        telefone.setId(id);
        return telefone;
    }
}
