package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Transaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TransaccionRepository;

import java.util.List;

public class TransaccionServices {

    // Inyección de dependencias
    TransaccionRepository transaccionRepository;

    public TransaccionServices(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public Double getTotalSaldoTransacciones(Long usuarioId) {
        return transaccionRepository.getTotalSaldoTransacciones(usuarioId);
    }

    public boolean saveOrUpdateTransaccion(Transaccion transaccion) {
        transaccionRepository.saveOrUpdateTransaccion(transaccion);
        return true;
    }

    public List<Transaccion> getAllTransacciones() {
        return transaccionRepository.getAllTransacciones();
    }

    public List<Transaccion> getAllTransaccionesByUsuario(Integer usuarioId) {
        return transaccionRepository.getAllTransaccionesByUsuario(usuarioId);
    }

    public boolean deleteTransaccionById(Integer id){
        return transaccionRepository.deleteTransaccionById(id);
    }

    // Obtiene la cantidad de transacciones realizadas en la plataforma
    public Integer getCantidadTransacciones() {
        return transaccionRepository.getCantidadTransacciones();
    }

}
