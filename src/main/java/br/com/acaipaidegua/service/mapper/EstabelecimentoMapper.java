package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.EstabelecimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Estabelecimento and its DTO EstabelecimentoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EnderecoMapper.class, CaracteristicaMapper.class, BeneficiamentoMapper.class})
public interface EstabelecimentoMapper extends EntityMapper<EstabelecimentoDTO, Estabelecimento> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "caracteristica.id", target = "caracteristicaId")
    @Mapping(source = "beneficiamento.id", target = "beneficiamentoId")
    EstabelecimentoDTO toDto(Estabelecimento estabelecimento);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "enderecoId", target = "endereco")
    @Mapping(source = "caracteristicaId", target = "caracteristica")
    @Mapping(source = "beneficiamentoId", target = "beneficiamento")
    Estabelecimento toEntity(EstabelecimentoDTO estabelecimentoDTO);

    default Estabelecimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(id);
        return estabelecimento;
    }
}
