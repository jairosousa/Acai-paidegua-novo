package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.GeolocalizacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Geolocalizacao and its DTO GeolocalizacaoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeolocalizacaoMapper extends EntityMapper<GeolocalizacaoDTO, Geolocalizacao> {



    default Geolocalizacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Geolocalizacao geolocalizacao = new Geolocalizacao();
        geolocalizacao.setId(id);
        return geolocalizacao;
    }
}
