package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.estado;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.EstadoRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperEstado;

import java.util.ArrayList;
import java.util.List;

public class EstadoImplementacion implements EstadoRepository {

    // ? Inyección de dependencias
    private final EstadoJPARepository estadoJPARepository;
    private final MapperEstado mapperEstado;

    public EstadoImplementacion(EstadoJPARepository estadoJPARepository, MapperEstado mapperEstado) {
        this.estadoJPARepository = estadoJPARepository;
        this.mapperEstado = mapperEstado;
    }

    @Override
    public List<Estado> getAllEstados() {

        List<Estado> estadosList = new ArrayList<>();

        // obtener todos los usuarios de la base de datos y los  guardar en la lista usuarios, se tiene que hacer el mapeo de la entidad de la base de datos a la entidad de dominio
        //Regresa un objeto iterable, se puede acceder a cada uno de los usuarios y agregarlos a la lista

        estadoJPARepository.findAll().forEach(Estado -> estadosList.add(mapperEstado.EstadoJPAToEstadoDomain(Estado)));

        return estadosList;

    }
}

