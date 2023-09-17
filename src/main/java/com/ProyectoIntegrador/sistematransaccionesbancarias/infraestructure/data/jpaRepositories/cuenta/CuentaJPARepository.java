package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.CuentaJPAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaJPARepository extends CrudRepository<CuentaJPAEntity,Long>{

    // hay que traer la cuenta por id de un usuario
}
