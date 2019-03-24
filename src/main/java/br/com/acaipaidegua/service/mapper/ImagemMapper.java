package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.ImagemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Imagem and its DTO ImagemDTO.
 */
@Mapper(componentModel = "spring", uses = {EstabelecimentoMapper.class})
public interface ImagemMapper extends EntityMapper<ImagemDTO, Imagem> {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    ImagemDTO toDto(Imagem imagem);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento")
    Imagem toEntity(ImagemDTO imagemDTO);

    default Imagem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Imagem imagem = new Imagem();
        imagem.setId(id);
        return imagem;
    }
}
