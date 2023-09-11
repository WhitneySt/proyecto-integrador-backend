package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TransaccionJPAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionJPARepository extends CrudRepository<TransaccionJPAEntity, Integer> {
}
