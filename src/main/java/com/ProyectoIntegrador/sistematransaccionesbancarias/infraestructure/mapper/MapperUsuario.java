package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper;


import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MapperUsuario {


    Usuario UsuarioJPAToUsuarioDomain(UsuarioJPAEntity usuarioJPAEntity);
    UsuarioJPAEntity UsuarioDomainToUsuarioJPA(Usuario usuario);

    Usuario UsuarioDtoToUsuarioDomain(UsuarioDto usuarioDto);
    UsuarioDto UsuarioDomainToUsuarioDto(Usuario usuario);
}
