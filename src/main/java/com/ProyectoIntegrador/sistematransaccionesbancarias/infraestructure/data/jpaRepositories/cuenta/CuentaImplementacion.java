package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.CuentaRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.CuentaJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;

import java.util.Optional;

public class CuentaImplementacion implements CuentaRepository {

    // ?  Inyección de dependencias
    private final CuentaJPARepository cuentaJPARepository;
    private final MapperCuenta mapperCuenta;

    public CuentaImplementacion(CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta) {
        this.cuentaJPARepository = cuentaJPARepository;
        this.mapperCuenta = mapperCuenta;
    }

    @Override
    public boolean saveOrUpdateCuenta(Cuenta cuenta) {
        CuentaJPAEntity cuentaJPAEntity = mapperCuenta.CuentaDomainToCuentaJPA(cuenta);
        cuentaJPARepository.save(cuentaJPAEntity);
        return cuentaJPARepository.findById(cuenta.getId()).isPresent();
    }

    @Override
    public Optional<Cuenta> getCuentaByIdUsuario(Integer idUsuario) {
        Optional<CuentaJPAEntity> cuentaJpa = cuentaJPARepository.findByUsuarioId(idUsuario);
        Optional<Cuenta> cuenta = cuentaJpa.map(mapperCuenta::CuentaJPAToCuentaDomain); // Se utiliza el  mapperCuenta  para convertir la entidad JPA a un objeto de dominio  Cuenta . Se devuelve un  Optional  que puede contener la cuenta encontrada o estar vacío
        return cuenta;
    }

    @Override
    public Integer getCantidadCuentas() {
        Integer cantidadCuentas = cuentaJPARepository.getCantidadCuentas();
        return cantidadCuentas;
    }

    @Override
    public Integer getCantidadCuentasActivas() {
        int cantidadCuentasActivas = cuentaJPARepository.getCantidadCuentasActivas();
        return cantidadCuentasActivas;
    }

    @Override
    public Long getTotalDineroCuentas() {
        Long totalDineroCuentas = cuentaJPARepository.getTotalDineroCuentas();
        return totalDineroCuentas;
    }
}
