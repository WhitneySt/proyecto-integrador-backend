package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoTransaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TipoTransaccionRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TipoTransaccionJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTipoTransaccion;

import java.util.ArrayList;
import java.util.List;

public class TipoTransaccionImplementacion implements TipoTransaccionRepository {
    // ?  Inyección de dependencias
    private  final TipoTransaccionJPARepository tipoTransaccionJPARepository;
    private final MapperTipoTransaccion mapperTipoTransaccion;

    public TipoTransaccionImplementacion(TipoTransaccionJPARepository tipoTransaccionJPARepository, MapperTipoTransaccion mapperTipoTransaccion) {
        this.tipoTransaccionJPARepository = tipoTransaccionJPARepository;
        this.mapperTipoTransaccion = mapperTipoTransaccion;
    }

    @Override
    public List<TipoTransaccion> getAllTipoTransacciones() {
        List<TipoTransaccion> tipoTransaccionList = new ArrayList<>();
        tipoTransaccionJPARepository.findAll().forEach(tipoTransaccion -> tipoTransaccionList.add(mapperTipoTransaccion.TipoTransaccionJPAToTipoTransaccionDomain(tipoTransaccion)));

        return tipoTransaccionList;
    }

    @Override
    public TipoTransaccion getTipoTransaccionById(Integer id) {
        TipoTransaccionJPAEntity tipoTransaccionJPAEntity = tipoTransaccionJPARepository.findById(id).get();
        return mapperTipoTransaccion.TipoTransaccionJPAToTipoTransaccionDomain(tipoTransaccionJPAEntity);
    }

    @Override
    public boolean saveOrUpdateTipoTransaccion(TipoTransaccion tipoTransaccion) {
        tipoTransaccionJPARepository.save(mapperTipoTransaccion.TipoTransaccionDomainToTipoTransaccionJPA(tipoTransaccion));
        return tipoTransaccionJPARepository.findById(tipoTransaccion.getId()).isPresent();
    }

    @Override
    public boolean deleteTipoTransaccionById(Integer id) {
        tipoTransaccionJPARepository.deleteById(id);
        return tipoTransaccionJPARepository.findById(id).isEmpty();
    }
}
