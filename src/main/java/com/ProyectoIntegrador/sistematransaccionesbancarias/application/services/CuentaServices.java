package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.CuentaRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;

import java.util.List;
import java.util.Optional;

public class CuentaServices {
    CuentaRepository cuentaRepository;

    public CuentaServices(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public boolean saveOrUpdateCuenta(Cuenta cuenta) {
        return cuentaRepository.saveOrUpdateCuenta(cuenta);
    }

    public Cuenta getCuentaByIdUsuario(Integer idUsuario) {
        Optional<Cuenta> cuenta = cuentaRepository.getCuentaByIdUsuario(idUsuario); // obtiene la cuenta del usuario
        return cuenta.orElseThrow(() -> new CuentaNotFoundException("No se encontró la cuenta del usuario con id: " + idUsuario));
    }

    public Integer getCantidadCuentas() {
        return cuentaRepository.getCantidadCuentas();
    }

    public Integer getCantidadCuentasActivas() {
        return cuentaRepository.getCantidadCuentasActivas();
    }

    public Long getTotalDineroCuentas() {
        return cuentaRepository.getTotalDineroCuentas();
    }

    public Long getPromedioDineroCuentas() {
        return cuentaRepository.getPromedioDineroCuentas();
    }

    public Integer getCantidadCuentasConMetas() {
        return cuentaRepository.getCantidadCuentasConMetas();
    }

    public Integer getCantidadCuentasCumplenMetaAhorro() {
        return cuentaRepository.getCantidadCuentasCumplenMetaAhorro();
    }

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.getAllCuentas();
    }


}
