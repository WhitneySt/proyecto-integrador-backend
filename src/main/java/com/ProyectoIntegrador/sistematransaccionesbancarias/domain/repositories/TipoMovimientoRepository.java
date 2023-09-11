package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoMovimiento;

import java.util.List;

public interface TipoMovimientoRepository {
    public List<TipoMovimiento> getAllTipoMovimientos();
    public TipoMovimiento getTipoMovimientoById(Integer id);
    public boolean  saveOrUpdateTipoMovimiento(TipoMovimiento tipoMovimiento);
    public boolean deleteTipoMovimientoById(Integer id);
}
