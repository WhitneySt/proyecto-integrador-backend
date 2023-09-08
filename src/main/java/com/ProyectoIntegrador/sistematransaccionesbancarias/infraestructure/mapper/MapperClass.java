package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;


import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperClass {


    Usuario UsuarioJPAToUsuarioDomain(UsuarioJPAEntity usuarioJPAEntity);
    UsuarioJPAEntity UsuarioDomainToUsuarioJPA(Usuario usuario);

    Usuario UsuarioDtoToUsuarioDomain(UsuarioDto usuarioDto);
    UsuarioDto UsuarioDomainToUsuarioDto(Usuario usuario);
}
