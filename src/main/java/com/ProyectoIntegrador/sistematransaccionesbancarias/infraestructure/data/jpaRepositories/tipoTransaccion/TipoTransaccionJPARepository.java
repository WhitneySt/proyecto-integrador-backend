package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.BolsilloJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoTransaccionJPAEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoTransaccionJPARepository extends CrudRepository<TipoTransaccionJPAEntity, Integer> {
    @Query(value = "SELECT * FROM tipo_transaccion WHERE nombre = ?1", nativeQuery = true)
    TipoTransaccionJPAEntity findTipoTransaccionByName(String nombre);
}
