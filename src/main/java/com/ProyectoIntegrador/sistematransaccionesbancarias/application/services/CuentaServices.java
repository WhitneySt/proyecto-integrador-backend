package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.CuentaRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;

import java.util.Optional;

public class CuentaServices {

    // inyección de dependencias
    CuentaRepository cuentaRepository;

    public CuentaServices(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public boolean saveOrUpdateCuenta(Cuenta cuenta) {

        Usuario usuarioCuenta = cuenta.getUsuarioId(); // obtiene el usuario de la cuenta
        Integer idUsuario = usuarioCuenta.getId(); // obtiene el id del usuario de la cuenta

        cuentaRepository.saveOrUpdateCuenta(cuenta);
        if (cuentaRepository.getCuentaByIdUsuario(idUsuario) != null) // si al buscar el usuario con el id del usuario, entonces se creó o actualizó correctamente
            return true;
        return true;
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




}
