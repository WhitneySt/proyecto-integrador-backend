package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.CuentaJPAEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaJPARepository extends CrudRepository<CuentaJPAEntity,Long>{

    // hay que traer la cuenta por id de un usuario
    // consulta sql
    @Query(value = "SELECT * FROM cuentas WHERE usuario_id = ?1", nativeQuery = true)
    Optional<CuentaJPAEntity> findByUsuarioId(Integer usuarioId);
}
