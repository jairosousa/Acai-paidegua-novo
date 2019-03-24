package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.RedeSociaisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RedeSociais and its DTO RedeSociaisDTO.
 */
@Mapper(componentModel = "spring", uses = {EstabelecimentoMapper.class})
public interface RedeSociaisMapper extends EntityMapper<RedeSociaisDTO, RedeSociais> {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    RedeSociaisDTO toDto(RedeSociais redeSociais);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento")
    RedeSociais toEntity(RedeSociaisDTO redeSociaisDTO);

    default RedeSociais fromId(Long id) {
        if (id == null) {
            return null;
        }
        RedeSociais redeSociais = new RedeSociais();
        redeSociais.setId(id);
        return redeSociais;
    }
}
