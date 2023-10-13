package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;

import java.util.List;

public interface BolsilloRepository {
    public List<Bolsillo> getAllBolsillos();
    public List<Bolsillo> getAllBolsillosByCuenta(Long cuentaId);
    public Bolsillo getBolsilloById(Integer id);
    public boolean  saveOrUpdateBolsillo(Bolsillo bolsillo);
    public boolean deleteBolsilloById(Integer id);

    public Double getTotalSaldoBolsillos(Long id);
}
