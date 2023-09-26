package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.EstadoRepository;

import java.util.List;

public class EstadoService {

    EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> getAllEstados() {
        return estadoRepository.getAllEstados();
    }

}
