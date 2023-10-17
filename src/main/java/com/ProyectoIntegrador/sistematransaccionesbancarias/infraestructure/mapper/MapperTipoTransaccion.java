package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoTransaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TipoTransaccionDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoTransaccionJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperTipoTransaccion {
    TipoTransaccion TipoTransaccionJPAToTipoTransaccionDomain(TipoTransaccionJPAEntity tipoTransaccionJPAEntity);
    TipoTransaccionJPAEntity TipoTransaccionDomainToTipoTransaccionJPA(TipoTransaccion tipoTransaccion);

    TipoTransaccion TipoTransaccionDtoToTipoTransaccionDomain(TipoTransaccionDto tipoTransaccionDto);
    TipoTransaccionDto TipoTransaccionDomainToTipoTransaccionDto(TipoTransaccion tipoTransaccion);
}
