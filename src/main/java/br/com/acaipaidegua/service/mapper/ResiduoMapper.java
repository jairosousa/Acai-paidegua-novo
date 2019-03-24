package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.ResiduoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Residuo and its DTO ResiduoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResiduoMapper extends EntityMapper<ResiduoDTO, Residuo> {



    default Residuo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Residuo residuo = new Residuo();
        residuo.setId(id);
        return residuo;
    }
}
