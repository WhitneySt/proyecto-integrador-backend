package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;

import java.util.Optional;

public interface CuentaRepository {

    public boolean saveOrUpdateCuenta(Cuenta cuenta);
    public Optional<Cuenta> getCuentaByIdUsuario(Integer idUsuario);

//     public boolean transferencia(Integer idCuentaOrigen, Integer idCuentaDestino, Double monto);

}
