package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;

public class BolsilloServices {

    // Inyección de dependencias
    BolsilloRepository bolsilloRepository;

    public BolsilloServices(BolsilloRepository bolsilloRepository) {
        this.bolsilloRepository = bolsilloRepository;
    }

    public Double getTotalSaldoBolsillos(Long id) {
        return bolsilloRepository.getTotalSaldoBolsillos(id);
    }
}
