package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.Rol;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.RolRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.estado.EstadoJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperRol;

import java.util.ArrayList;
import java.util.List;

public class RolImplementacion implements RolRepository {

    // ? Inyección de dependencias
    private final RolJPARepository rolJPARepository;
    private final MapperRol mapperRol;

    public RolImplementacion(RolJPARepository rolJPARepository, MapperRol mapperRol) {
            this.rolJPARepository = rolJPARepository;
            this.mapperRol = mapperRol;
    }

    @Override
    public List<Rol> getAllRoles() {

            List<Rol> rolesList = new ArrayList<>();

            // obtener todos los usuarios de la base de datos y los  guardar en la lista usuarios, se tiene que hacer el mapeo de la entidad de la base de datos a la entidad de dominio
            //Regresa un objeto iterable, se puede acceder a cada uno de los usuarios y agregarlos a la lista

            rolJPARepository.findAllRoles().forEach(Rol -> rolesList.add(mapperRol.RolJPAToRolDomain(Rol)));

            return rolesList;
    }
}
