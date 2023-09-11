package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.BolsilloJPAEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolsilloJPARepository extends CrudRepository<BolsilloJPAEntity, Integer> {
}
