package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Transaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TransaccionRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.TransaccionJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTransaccion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransaccionImplementacion implements TransaccionRepository {
    // ?  Inyección de dependencias
    private  final TransaccionJPARepository transaccionJPARepository;
    private final MapperTransaccion mapperTransaccion;

    public TransaccionImplementacion(TransaccionJPARepository transaccionJPARepository, MapperTransaccion mapperTransaccion) {
        this.transaccionJPARepository = transaccionJPARepository;
        this.mapperTransaccion = mapperTransaccion;
    }

    @Override
    public List<Transaccion> getAllTransacciones() {
        List<Transaccion> transaccionList = new ArrayList<>();
        transaccionJPARepository.findAll().forEach(transaccion -> transaccionList.add(mapperTransaccion.TransaccionJPAToTransaccionDomain(transaccion)));

        return transaccionList;
    }

    @Override
    public List<Transaccion> getAllTransaccionesByUsuario(Integer usuarioId) {
        List<Transaccion> transaccionList = new ArrayList<>();
        transaccionJPARepository.getAllTransaccionesByUsuario(usuarioId).forEach(transaccion -> transaccionList.add(mapperTransaccion.TransaccionJPAToTransaccionDomain(transaccion)));

        return transaccionList;
    }

    @Override
    public Transaccion getTransaccionById(Integer id) {
        TransaccionJPAEntity transaccionJPAEntity = transaccionJPARepository.findById(id).get();
        return mapperTransaccion.TransaccionJPAToTransaccionDomain(transaccionJPAEntity);
    }

    @Override
    public boolean saveOrUpdateTransaccion(Transaccion transaccion) {
        TransaccionJPAEntity transaccionJPAEntity = mapperTransaccion.TransaccionDomainToTransaccionJPA(transaccion);
        transaccionJPARepository.save(transaccionJPAEntity);
        return transaccionJPARepository.findById(transaccionJPAEntity.getId()).isPresent();
    }

    @Override
    public boolean deleteTransaccionById(Integer id) {
        transaccionJPARepository.deleteById(id);
        return transaccionJPARepository.findById(id).isEmpty();
    }

    @Override
    public Double getTotalSaldoTransacciones(Long usuarioId) {
        return transaccionJPARepository.getTotalSaldoTransacciones(usuarioId);
    }

    @Override
    public Integer getCantidadTransacciones() {
        return transaccionJPARepository.getCantidadTransacciones();
    }

    @Override
    public BigDecimal getTotalDineroTransacciones() {
        return transaccionJPARepository.obtenerSumaDeMontosTransacciones();
    }


}
