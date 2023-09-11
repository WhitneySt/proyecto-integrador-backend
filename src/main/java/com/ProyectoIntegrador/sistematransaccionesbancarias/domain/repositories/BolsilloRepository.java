package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;

import java.util.List;

public interface BolsilloRepository {
    public List<Bolsillo> getAllBolsillos();
    public Bolsillo getBolsilloById(Integer id);
    public boolean  saveOrUpdateBolsillo(Bolsillo bolsillo);
    public boolean deleteBolsilloById(Integer id);
}
