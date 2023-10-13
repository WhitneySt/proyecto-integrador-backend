package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;

import java.util.List;

public class BolsilloServices {

    // Inyección de dependencias
    BolsilloRepository bolsilloRepository;

    public BolsilloServices(BolsilloRepository bolsilloRepository) {
        this.bolsilloRepository = bolsilloRepository;
    }

    public Double getTotalSaldoBolsillos(Long id) {
        return bolsilloRepository.getTotalSaldoBolsillos(id);
    }


    public boolean saveOrUpdateBolsillo(Bolsillo bolsillo) {
        bolsilloRepository.saveOrUpdateBolsillo(bolsillo);
        return true;
    }

    public List<Bolsillo> getAllBolsillos() {
        return bolsilloRepository.getAllBolsillos();
    }

    public List<Bolsillo> getAllBolsillosByCuenta(Long cuentaId) {
        return bolsilloRepository.getAllBolsillosByCuenta(cuentaId);
    }

    public boolean deleteBolsilloById(Integer id){
        return bolsilloRepository.deleteBolsilloById(id);
    }

}
