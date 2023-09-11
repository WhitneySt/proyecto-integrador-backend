package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoMovimiento;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoMovimientoJPAEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMovimientoJPARepository extends CrudRepository<TipoMovimientoJPAEntity, Integer> {
}
