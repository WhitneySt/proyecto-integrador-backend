package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Transaccion;

import java.math.BigDecimal;
import java.util.List;

public interface TransaccionRepository {
    public List<Transaccion> getAllTransacciones();
    public List<Transaccion> getAllTransaccionesByUsuario(Integer usuarioId);
    public Transaccion getTransaccionById(Integer id);
    public boolean  saveOrUpdateTransaccion(Transaccion transaccion);
    public boolean deleteTransaccionById(Integer id);

    public Double getTotalSaldoTransacciones(Long usuarioId);

    public Integer getCantidadTransacciones();

    public BigDecimal getTotalDineroTransacciones();

}
