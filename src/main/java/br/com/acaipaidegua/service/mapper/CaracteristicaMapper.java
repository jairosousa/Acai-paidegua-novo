package br.com.acaipaidegua.service.mapper;

import br.com.acaipaidegua.domain.*;
import br.com.acaipaidegua.service.dto.CaracteristicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Caracteristica and its DTO CaracteristicaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CaracteristicaMapper extends EntityMapper<CaracteristicaDTO, Caracteristica> {



    default Caracteristica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId(id);
        return caracteristica;
    }
}
