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

    // Obtiene el total de retiros de un usuario
    @Query(value = "SELECT COALESCE(SUM(monto), 0) FROM transaccion WHERE usuario_id = ?1 AND id_tipo_transaccion = 2", nativeQuery = true)
    public BigDecimal getTotalRetirosByIdUsuario(Long usuarioId);

    // Obtiene el total de transferencias de un usuario
    @Query(value = "SELECT COALESCE(SUM(monto), 0) FROM transaccion WHERE usuario_id = ?1 AND id_tipo_transaccion = 3", nativeQuery = true)
    public BigDecimal getTotalTransferenciasByIdUsuario(Long usuarioId);

    // Obtiene el balance neto de un usuario
    // El balance neto se clacula  restando el total de retiros del total de depósitos y transferencias.
    // Ejemplo SELECT
    //    usuario_id,
    //    SUM(CASE WHEN id_tipo_transaccion = 1 THEN monto ELSE 0 END) AS total_depositos,
    //    SUM(CASE WHEN id_tipo_transaccion = 2 THEN monto ELSE 0 END) AS total_retiros,
    //    SUM(CASE WHEN id_tipo_transaccion = 3 THEN monto ELSE 0 END) AS total_transferencias,
    //    (SUM(CASE WHEN id_tipo_transaccion = 1 THEN monto ELSE 0 END) -
    //    (SUM(CASE WHEN id_tipo_transaccion = 2 THEN monto ELSE 0 END) +
    //    SUM(CASE WHEN id_tipo_transaccion = 3 THEN monto ELSE 0 END))) AS balance_neto
    //FROM sistemabanca.transaccion
    //WHERE usuario_id =65632480
    //GROUP BY usuario_id;
    @Query(value = "SELECT COALESCE(SUM(CASE WHEN id_tipo_transaccion = 1 THEN monto ELSE 0 END) - (SUM(CASE WHEN id_tipo_transaccion = 2 THEN monto ELSE 0 END) + SUM(CASE WHEN id_tipo_transaccion = 3 THEN monto ELSE 0 END)), 0) AS balance_neto FROM transaccion WHERE usuario_id = ?1", nativeQuery = true)
    public BigDecimal getBalanceNetoByIdUsuario(Long usuarioId);


    /*
    @Query(value="select COALESCE(SUM(t.monto), 0) from transaccion t inner join tipo_transaccion tt on tt.id = t.id_tipo_transaccion inner join cuentas c on c.id = t.id_cuenta_destino where tt.nombre in (\"deposito\", \"transferencia\") and c.usuario_id = ?1", nativeQuery = true)
    public Double getTotalSaldoTransferencias(Integer idUsuario);*/
}
