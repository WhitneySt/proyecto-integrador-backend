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

    /*
    @Query(value="select COALESCE(SUM(t.monto), 0) from transaccion t inner join tipo_transaccion tt on tt.id = t.id_tipo_transaccion inner join cuentas c on c.id = t.id_cuenta_destino where tt.nombre in (\"deposito\", \"transferencia\") and c.usuario_id = ?1", nativeQuery = true)
    public Double getTotalSaldoTransferencias(Integer idUsuario);*/
}
