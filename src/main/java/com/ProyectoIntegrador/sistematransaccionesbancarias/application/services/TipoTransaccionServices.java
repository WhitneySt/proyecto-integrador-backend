package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoTransaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TipoTransaccionRepository;

import java.util.List;

public class TipoTransaccionServices {

    // Inyección de dependencias
    TipoTransaccionRepository tipoTransaccionRepository;

    public TipoTransaccionServices(TipoTransaccionRepository tipoTransaccionRepository) {
        this.tipoTransaccionRepository = tipoTransaccionRepository;
    }

    public boolean saveOrUpdateTipoTransaccion(TipoTransaccion tipoTransaccion) {
        tipoTransaccionRepository.saveOrUpdateTipoTransaccion(tipoTransaccion);
        return true;
    }

    public List<TipoTransaccion> getAllTipoTransaccion() {
        return tipoTransaccionRepository.getAllTipoTransacciones();
    }

    public boolean deleteTipoTransaccionById(Integer id){
        return tipoTransaccionRepository.deleteTipoTransaccionById(id);
    }

    public TipoTransaccion getTipoTransaccionByName(String nombre) {
        return tipoTransaccionRepository.getTipoTransaccionByName(nombre);
    }

}
