package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.EmailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Email and its DTO EmailDTO.
 */
@Mapper(componentModel = "spring", uses = {EstabelecimentoMapper.class})
public interface EmailMapper extends EntityMapper<EmailDTO, Email> {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    EmailDTO toDto(Email email);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento")
    Email toEntity(EmailDTO emailDTO);

    default Email fromId(Long id) {
        if (id == null) {
            return null;
        }
        Email email = new Email();
        email.setId(id);
        return email;
    }
}
