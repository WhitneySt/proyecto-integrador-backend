package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.RolJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperRol {

    Rol RolJPAToRolDomain(RolJPAEntity rolJPAEntity);
    RolJPAEntity RolDomainToRolJPA(Rol rol);

}
