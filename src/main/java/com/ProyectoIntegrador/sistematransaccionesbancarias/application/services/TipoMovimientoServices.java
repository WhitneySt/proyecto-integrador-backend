package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoMovimiento;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoTransaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TipoMovimientoRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TipoTransaccionRepository;

import java.util.List;

public class TipoMovimientoServices {

    TipoMovimientoRepository tipoMovimientoRepository;

    public TipoMovimientoServices(TipoMovimientoRepository tipoMovimientoRepository) {
        this.tipoMovimientoRepository = tipoMovimientoRepository;
    }

    public boolean saveOrUpdateTipoTransaccion(TipoMovimiento tipoMovimiento) {
        tipoMovimientoRepository.saveOrUpdateTipoMovimiento(tipoMovimiento);
        return true;
    }

    public List<TipoMovimiento> getAllTipoMovimientos() {
        return tipoMovimientoRepository.getAllTipoMovimientos();
    }

    public boolean deleteTipoMovimientoById(Integer id){
        return tipoMovimientoRepository.deleteTipoMovimientoById(id);
    }

    public TipoMovimiento getTipoMovimientoByCodes(String codigoOrigen, String codigoDestino) {
        return tipoMovimientoRepository.findTipoMovimientoByCodes(codigoOrigen, codigoDestino);
    }

}
