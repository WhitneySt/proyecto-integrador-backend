package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TransaccionJPAEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransaccionJPARepository extends CrudRepository<TransaccionJPAEntity, Integer> {
    @Query(value = "SELECT * FROM transaccion WHERE usuario_id = ?1", nativeQuery = true)
    List<TransaccionJPAEntity> getAllTransaccionesByUsuario(Integer usuarioId);

    @Query(value="SELECT COALESCE(SUM(saldo), 0) FROM transaccion WHERE usuario_id =?1", nativeQuery = true)
    public Double getTotalSaldoTransacciones(Long usuarioId);

    @Query(value="SELECT COUNT(*) FROM transaccion", nativeQuery = true)
    public Integer getCantidadTransacciones();

    // Obtiene la cantidad de dinero de las transacciones realizadas en la plataforma
    @Query(value = "SELECT SUM(t.monto) FROM transaccion t", nativeQuery = true)
    BigDecimal obtenerSumaDeMontosTransacciones();

    // get cantidad_depositos
    @Query(value = "SELECT COUNT(*) FROM transaccion WHERE id_tipo_transaccion = 1", nativeQuery = true)
    public Integer getCantidadDepositos();

    // get cantidad_retiros
    @Query(value = "SELECT COUNT(*) FROM transaccion WHERE id_tipo_transaccion = 2", nativeQuery = true)
    public Integer getCantidadRetiros();

    // get cantidad_transferencias
    @Query(value = "SELECT COUNT(*) FROM transaccion WHERE id_tipo_transaccion = 3", nativeQuery = true)
    public Integer getCantidadTransferencias();

    // get total_depositos_by_id_usuario
    @Query(value = "SELECT COALESCE(SUM(monto), 0) FROM transaccion WHERE usuario_id = ?1 AND id_tipo_transaccion = 1", nativeQuery = true)
    public BigDecimal getTotalDepositosByIdUsuario(Long usuarioId);



}
