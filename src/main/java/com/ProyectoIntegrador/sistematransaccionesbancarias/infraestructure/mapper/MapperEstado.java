package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.EstadoJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperEstado {

    Estado EstadoJPAToEstadoDomain(EstadoJPAEntity estadoJPAEntity);
    EstadoJPAEntity EstadoDomainToEstadoJPA(Estado estado);


}
