package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.CuentaRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;

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
        cuentaJPARepository.save(mapperCuenta.CuentaDomainToCuentaJPA(cuenta));
        return cuentaJPARepository.findById(cuenta.getId()).isPresent();
    }

    @Override
    public Cuenta getCuentaByIdUsuario(Integer idUsuario) {
        return null;
    }
}
