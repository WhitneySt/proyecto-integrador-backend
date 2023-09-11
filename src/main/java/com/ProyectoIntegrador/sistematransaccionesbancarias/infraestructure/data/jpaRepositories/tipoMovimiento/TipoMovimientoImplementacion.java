package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoMovimiento;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoMovimiento;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TipoMovimientoRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoMovimientoJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTipoMovimiento;

import java.util.ArrayList;
import java.util.List;

public class TipoMovimientoImplementacion implements TipoMovimientoRepository {
    private  final TipoMovimientoJPARepository tipoMovimientoJPARepository;
    private final MapperTipoMovimiento mapperTipoMovimiento;

    public TipoMovimientoImplementacion(TipoMovimientoJPARepository tipoMovimientoJPARepository, MapperTipoMovimiento mapperTipoMovimiento) {
        this.tipoMovimientoJPARepository = tipoMovimientoJPARepository;
        this.mapperTipoMovimiento = mapperTipoMovimiento;
    }

    @Override
    public List<TipoMovimiento> getAllTipoMovimientos() {
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<>();
        tipoMovimientoJPARepository.findAll().forEach(tipoMovimiento -> tipoMovimientoList.add(mapperTipoMovimiento.TipoMovimientoJPAToTipoTransaccionDomain(tipoMovimiento)));

        return tipoMovimientoList;
    }

    @Override
    public TipoMovimiento getTipoMovimientoById(Integer id) {
        TipoMovimientoJPAEntity tipoMovimientoJPAEntity = tipoMovimientoJPARepository.findById(id).get();
        return mapperTipoMovimiento.TipoMovimientoJPAToTipoTransaccionDomain(tipoMovimientoJPAEntity);
    }

    @Override
    public boolean saveOrUpdateTipoMovimiento(TipoMovimiento tipoMovimiento) {
        tipoMovimientoJPARepository.save(mapperTipoMovimiento.TipoMovimientoDomainToTipoMovimientoJPA(tipoMovimiento));
        return tipoMovimientoJPARepository.findById(tipoMovimiento.getId()).isPresent();
    }

    @Override
    public boolean deleteTipoMovimientoById(Integer id) {
        tipoMovimientoJPARepository.deleteById(id);
        return tipoMovimientoJPARepository.findById(id).isEmpty();
    }
}
