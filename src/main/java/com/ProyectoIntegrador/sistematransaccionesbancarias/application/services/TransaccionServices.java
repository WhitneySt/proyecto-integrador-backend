package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Transaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TransaccionRepository;

import java.math.BigDecimal;
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

    public Integer getCantidadTransacciones() {
        return transaccionRepository.getCantidadTransacciones();
    }

    public BigDecimal getTotalDineroTransacciones() {
        return transaccionRepository.getTotalDineroTransacciones();
    }

    public Integer getCantidadDepositos() {
        return transaccionRepository.getCantidadDepositos();
    }

    public Integer getCantidadRetiros() {
        return transaccionRepository.getCantidadRetiros();
    }

    public Integer getCantidadTransferencias() {
        return transaccionRepository.getCantidadTransferencias();
    }

    public BigDecimal getTotalDepositosByIdUsuario(Long usuarioId) {
        return transaccionRepository.getTotalDepositosByIdUsuario(usuarioId);
    }

    // Obtiene el total de retiros de un usuario
    public BigDecimal getTotalRetirosByIdUsuario(Long usuarioId) {
        return transaccionRepository.getTotalRetirosByIdUsuario(usuarioId);
    }

    public BigDecimal getTotalTransferenciasByIdUsuario(Long usuarioId) {
        return transaccionRepository.getTotalTransferenciasByIdUsuario(usuarioId);
    }

    public BigDecimal getBalanceNetoById(Long usuarioId) {
        return transaccionRepository.getBalanceNetoByIdUsuario(usuarioId);
    }


}
