package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.CuentaDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.CuentaJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperCuenta {

    Cuenta CuentaJPAToCuentaDomain(CuentaJPAEntity cuentaJPAEntity);

    CuentaJPAEntity CuentaDomainToCuentaJPA(Cuenta cuenta);

    Cuenta CuentaDtoToCuentaDomain(CuentaDto cuentaDto);
    CuentaJPAEntity CuentaDomainToCuentaDto(Cuenta cuenta);
}
