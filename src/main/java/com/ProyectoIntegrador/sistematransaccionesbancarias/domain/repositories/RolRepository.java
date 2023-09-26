package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;

import java.util.List;

public interface RolRepository {

    // obtener todos los roles
    public List<Rol> getAllRoles();
}
