package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoTransaccionJPAEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTransaccionJPARepository extends CrudRepository<TipoTransaccionJPAEntity, Integer> {
}
