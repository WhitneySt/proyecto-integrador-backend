package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoTransaccion;

import java.util.List;

public interface TipoTransaccionRepository {
    public List<TipoTransaccion> getAllTipoTransacciones();
    public TipoTransaccion getTipoTransaccionById(Integer id);
    public boolean  saveOrUpdateTipoTransaccion(TipoTransaccion tipoTransaccion);
    public boolean deleteTipoTransaccionById(Integer id);
}
