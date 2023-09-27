package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.Rol;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.RolJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolJPARepository extends CrudRepository<RolJPAEntity, Integer> {

    @Query(value = "SELECT * FROM roles", nativeQuery = true)
    List<RolJPAEntity> findAllRoles();
}
