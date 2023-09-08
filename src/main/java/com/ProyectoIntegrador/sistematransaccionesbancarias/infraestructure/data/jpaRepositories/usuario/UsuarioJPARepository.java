package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioJPARepository extends CrudRepository<UsuarioJPAEntity,Integer> {
}
