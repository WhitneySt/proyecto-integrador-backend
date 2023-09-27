package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.RolRepository;

import java.util.List;

public class RolService {

    RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // obtener todos los roles
    public List<Rol> getAllRoles() {
        return rolRepository.getAllRoles();
    }
}
