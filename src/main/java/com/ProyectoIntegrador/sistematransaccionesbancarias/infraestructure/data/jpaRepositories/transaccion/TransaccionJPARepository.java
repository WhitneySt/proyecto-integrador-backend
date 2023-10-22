package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TransaccionJPAEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionJPARepository extends CrudRepository<TransaccionJPAEntity, Integer> {
    @Query(value = "SELECT * FROM transaccion WHERE usuario_id = ?1", nativeQuery = true)
    List<TransaccionJPAEntity> getAllTransaccionesByUsuario(Integer usuarioId);

    @Query(value="SELECT COALESCE(SUM(saldo), 0) FROM transaccion WHERE usuario_id =?1", nativeQuery = true)
    public Double getTotalSaldoTransacciones(Long usuarioId);

    @Query(value="SELECT COUNT(*) FROM transaccion", nativeQuery = true)
    public Integer getCantidadTransacciones();
}
