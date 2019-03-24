package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Endereco and its DTO EnderecoDTO.
 */
@Mapper(componentModel = "spring", uses = {GeolocalizacaoMapper.class})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {

    @Mapping(source = "gelocalizacao.id", target = "gelocalizacaoId")
    EnderecoDTO toDto(Endereco endereco);

    @Mapping(source = "gelocalizacaoId", target = "gelocalizacao")
    Endereco toEntity(EnderecoDTO enderecoDTO);

    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
