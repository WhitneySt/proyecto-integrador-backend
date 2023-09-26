package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.estado;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.EstadoJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoJPARepository extends JpaRepository<EstadoJPAEntity, Integer> {
}
