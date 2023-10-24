package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoMovimiento;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoMovimientoJPAEntity;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMovimientoJPARepository extends CrudRepository<TipoMovimientoJPAEntity, Integer> {

    @Query(value = "SELECT * FROM tipo_movimiento WHERE codigo_origen = ?1 and codigo_destino = ?2", nativeQuery = true)
    TipoMovimientoJPAEntity findTipoMovimientoByCodes(String codigoOrigen, String codigoDestino);
}
