package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoMovimiento;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TipoMovimientoDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TipoTransaccionDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoMovimientoJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoTransaccionJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperTipoMovimiento {
    TipoMovimiento TipoMovimientoJPAToTipoTransaccionDomain(TipoMovimientoJPAEntity tipoMovimientoJPAEntity);
    TipoMovimientoJPAEntity TipoMovimientoDomainToTipoMovimientoJPA(TipoMovimiento tipoMovimiento);

    TipoMovimiento TipoMovimientoDtoToTipoMovimientoDomain(TipoMovimientoDto tipoMovimientoDto);
    TipoMovimientoDto TipoMovimientoDomainToTipoMovimientoDto(TipoMovimiento tipoMovimiento);
}
