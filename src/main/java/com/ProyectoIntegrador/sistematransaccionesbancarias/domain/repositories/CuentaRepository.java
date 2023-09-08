package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;

public interface CuentaRepository {

    public boolean saveOrUpdateCuenta(Cuenta cuenta);
    public Cuenta getCuentaByIdUsuario(Integer idUsuario);

}
