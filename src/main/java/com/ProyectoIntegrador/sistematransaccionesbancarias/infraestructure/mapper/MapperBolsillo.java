package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.BolsilloDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.BolsilloJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperBolsillo {
    Bolsillo BolsilloJPAToBolsilloDomain(BolsilloJPAEntity bolsilloJPAEntity);
    BolsilloJPAEntity BolsilloDomainToBolsilloJPA(Bolsillo bolsillo);

    Bolsillo BolsilloDtoToBolsilloDomain(BolsilloDto bolsilloDto);
    BolsilloDto BolsilloDomainToBolsilloDto(Bolsillo bolsillo);
}
