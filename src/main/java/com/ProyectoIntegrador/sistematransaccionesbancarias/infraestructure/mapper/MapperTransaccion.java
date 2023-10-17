package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;


import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Transaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TransaccionDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TransaccionJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperTransaccion {
    Transaccion TransaccionJPAToTransaccionDomain(TransaccionJPAEntity transaccionJPAEntity);
    TransaccionJPAEntity TransaccionDomainToTransaccionJPA(Transaccion transaccion);

    Transaccion TransaccionDtoToTransaccionDomain(TransaccionDto transaccionDto);
    TransaccionDto TransaccionDomainToTransaccionDto(Transaccion transaccion);
}
