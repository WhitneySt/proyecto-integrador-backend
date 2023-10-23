package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository {

    public boolean saveOrUpdateCuenta(Cuenta cuenta);
    public Optional<Cuenta> getCuentaByIdUsuario(Integer idUsuario);
    public Integer getCantidadCuentas();

    public Integer getCantidadCuentasActivas();

    public Long  getTotalDineroCuentas();

    public Long getPromedioDineroCuentas();

    public Integer getCantidadCuentasConMetas();

    // obtener la cantidad de cuentas que cumplen con la meta de ahorro
    public Integer getCantidadCuentasCumplenMetaAhorro();

    public List<Cuenta> getAllCuentas();

    public Cuenta getCuentaById(Long id);







}
