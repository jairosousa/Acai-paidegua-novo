package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.TabelaPrecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TabelaPreco and its DTO TabelaPrecoDTO.
 */
@Mapper(componentModel = "spring", uses = {EstabelecimentoMapper.class, ProdutoMapper.class})
public interface TabelaPrecoMapper extends EntityMapper<TabelaPrecoDTO, TabelaPreco> {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "produto.id", target = "produtoId")
    TabelaPrecoDTO toDto(TabelaPreco tabelaPreco);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento")
    @Mapping(source = "produtoId", target = "produto")
    TabelaPreco toEntity(TabelaPrecoDTO tabelaPrecoDTO);

    default TabelaPreco fromId(Long id) {
        if (id == null) {
            return null;
        }
        TabelaPreco tabelaPreco = new TabelaPreco();
        tabelaPreco.setId(id);
        return tabelaPreco;
    }
}
