package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.BolsilloJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperBolsillo;

import java.util.ArrayList;
import java.util.List;

public class BolsilloImplementacion implements BolsilloRepository {
    // ?  Inyección de dependencias
    private  final BolsilloJPARepository bolsilloJPARepository;
    private final MapperBolsillo mapperBolsillo;

    public BolsilloImplementacion(BolsilloJPARepository bolsilloJPARepository, MapperBolsillo mapperBolsillo) {
        this.bolsilloJPARepository = bolsilloJPARepository;
        this.mapperBolsillo = mapperBolsillo;
    }

    @Override
    public List<Bolsillo> getAllBolsillos() {
        List<Bolsillo> bolsilloList = new ArrayList<>();
        bolsilloJPARepository.findAll().forEach(bolsillo -> bolsilloList.add(mapperBolsillo.BolsilloJPAToBolsilloDomain(bolsillo)));
        return bolsilloList;
    }

    public List<Bolsillo> getAllBolsillosByCuenta(Long cuentaId) {
        List<Bolsillo> bolsilloList = new ArrayList<>();
        bolsilloJPARepository.getAllBolsillosByCuenta(cuentaId).forEach(bolsillo -> bolsilloList.add(mapperBolsillo.BolsilloJPAToBolsilloDomain(bolsillo)));
        return bolsilloList;
    }

    @Override
    public Bolsillo getBolsilloById(Integer id) {
        BolsilloJPAEntity bolsilloJPAEntity = bolsilloJPARepository.findById(id).get();
        return mapperBolsillo.BolsilloJPAToBolsilloDomain(bolsilloJPAEntity);
    }

    @Override
    public Bolsillo saveOrUpdateBolsillo(Bolsillo bolsillo) {
        BolsilloJPAEntity bolsilloJPAEntity = mapperBolsillo.BolsilloDomainToBolsilloJPA(bolsillo);
        bolsilloJPARepository.save(bolsilloJPAEntity);

        return mapperBolsillo.BolsilloJPAToBolsilloDomain(bolsilloJPAEntity);
    }

    @Override
    public boolean deleteBolsilloById(Integer id) {
        bolsilloJPARepository.deleteById(id);
        return bolsilloJPARepository.findById(id).isEmpty();
    }

    @Override
    public Double getTotalSaldoBolsillos(Long id) {
        return bolsilloJPARepository.getTotalSaldoBolsillos(id);
    }
}
