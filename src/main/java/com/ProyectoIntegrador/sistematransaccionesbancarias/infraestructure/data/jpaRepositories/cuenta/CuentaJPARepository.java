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

    // Obtener la cantidad de cuentas
    @Query(value = "SELECT COUNT(*) FROM cuentas", nativeQuery = true)
    Integer getCantidadCuentas();


    // Obtener la cantidad de cuentas activas
    //estado_id=1 es que un usuario esta activo
    @Query(value = "SELECT COUNT(*) FROM cuentas JOIN usuarios ON cuentas.usuario_id=usuarios.id  WHERE usuarios.estado_id=1", nativeQuery = true)
    Integer getCantidadCuentasActivas();

}
