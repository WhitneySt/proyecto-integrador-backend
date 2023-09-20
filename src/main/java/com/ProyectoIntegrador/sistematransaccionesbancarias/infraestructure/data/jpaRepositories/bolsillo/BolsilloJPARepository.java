package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.BolsilloJPAEntity;

import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolsilloJPARepository extends CrudRepository<BolsilloJPAEntity, Integer> {

    // ?  Query para obtener el total del saldo de los bolsillos de un usuario

    @Query(value="SELECT COALESCE(SUM(saldo), 0) FROM bolsillo WHERE id_cuenta =?1", nativeQuery = true)
    public Double getTotalSaldoBolsillos(Long id);
    /*
    *  Se utiliza  COALESCE  para manejar el caso en el que no se encuentre una cuenta con el ID especificado.
    *  Si la subconsulta devuelve un valor nulo (porque no se encontró una cuenta),
    *  entonces  COALESCE  reemplaza ese valor nulo por cero ( 0 ). Esto garantiza que siempre se devuelva un resultado,
    *  incluso si no hay una cuenta con el ID especificado.
    * */
}
