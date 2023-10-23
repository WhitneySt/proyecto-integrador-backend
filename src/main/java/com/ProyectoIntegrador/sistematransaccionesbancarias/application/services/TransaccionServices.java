package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.BolsilloRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.TransaccionRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TransaccionDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTipoMovimiento;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTransaccion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransaccionServices {

    // Inyección de dependencias
    TransaccionRepository transaccionRepository;

    public TransaccionServices(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public boolean saveOrUpdateTransaccion(CuentaServices cuentaServices, TipoTransaccionServices tipoTransaccionServices, BolsilloServices bolsilloServices, TipoMovimientoServices tipoMovimientoServices, MapperTransaccion mapperTransaccion, MapperTipoMovimiento mapperTipoMovimiento, TransaccionDto transaccionDto, Usuario usuario, String tipoTransaccion) {
        try {
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuario.getId());
            Double saldoDisponible = cuenta.getSaldoActual();
            Double monto = transaccionDto.getMonto();

            if(saldoDisponible < monto) {
                System.out.printf("rechazar la transaccion!");
                return false;
            }

            TipoTransaccion _tipoTransaccion = tipoTransaccionServices.getTipoTransaccionByName(tipoTransaccion);
            Bolsillo bolsilloOrigen = null;
            Bolsillo bolsilloDestino = null;

            transaccionDto.setIdTipoTransaccion(_tipoTransaccion);
            transaccionDto.setFechaTransaccion(new Date());
            transaccionDto.setUsuarioId(usuario);

            boolean esTransferencia = _tipoTransaccion.getNombre().equalsIgnoreCase("Transferencia");

            if(esTransferencia) {
                String cuentaTerceros = transaccionDto.getCuentaTerceros();
                Integer bolsilloOrigenId = transaccionDto.getBolsilloOrigenId();
                Integer bolsilloDestinoId = transaccionDto.getBolsilloDestinoId();
                TipoMovimiento tipoMovimiento = null;

                if(!cuentaTerceros.isEmpty() && bolsilloDestinoId == null && bolsilloOrigenId == null) { // Transferir de mi cuenta a otra cuenta
                    transaccionDto.setIdCuentaOrigen(cuenta);
                    transaccionDto.setCuentaTerceros(cuentaTerceros);
                    tipoMovimiento = tipoMovimientoServices.getTipoMovimientoByCodes("CU","CU");
                    transaccionDto.setIdTipoMovimiento(tipoMovimiento);

                    // Verificar si el numero de cuenta de terceros pertenece a una cuenta del mismo banco para agregarle el saldo transferido desde mi cuenta
                    Cuenta cuentaEncontrada = cuentaServices.getCuentaById(Long.parseLong(cuentaTerceros));
                    if(cuentaEncontrada != null) {
                        Double nuevoSaldo = cuentaEncontrada.getSaldo() + monto;
                        cuentaEncontrada.setSaldoActual(nuevoSaldo);
                        cuentaEncontrada.setFechaActualizacion(new Date());
                        transaccionDto.setIdCuentaDestino(cuentaEncontrada);
                        cuentaServices.saveOrUpdateCuenta(cuentaEncontrada);

                        Transaccion transaccionOtraCuenta = new Transaccion();
                        transaccionOtraCuenta.setIdTipoTransaccion(_tipoTransaccion);
                        transaccionOtraCuenta.setIdTipoMovimiento(tipoMovimiento);
                        transaccionOtraCuenta.setFechaTransaccion(new Date());
                        transaccionOtraCuenta.setUsuarioId(cuentaEncontrada.getUsuarioId());
                        transaccionOtraCuenta.setIdCuentaOrigen(cuenta);
                        transaccionOtraCuenta.setIdCuentaDestino(cuentaEncontrada);
                        transaccionOtraCuenta.setMonto(monto);
                        transaccionOtraCuenta.setDescripcion("DEPOSITO DESDE: " + usuario.getNombre());

                        transaccionRepository.saveOrUpdateTransaccion(transaccionOtraCuenta);

                    }

                    // restar de mi cuenta
                    cuenta.setSaldoActual(saldoDisponible - monto);
                    cuenta.setFechaActualizacion(new Date());
                    cuentaServices.saveOrUpdateCuenta(cuenta);
                } else if(cuentaTerceros.isEmpty() && bolsilloDestinoId != null && bolsilloOrigenId == null) { // Transferir de mi cuenta a un bolsillo
                    transaccionDto.setIdCuentaOrigen(cuenta);
                    bolsilloDestino = bolsilloServices.getBolsilloById(bolsilloDestinoId);
                    transaccionDto.setIdBolsilloDestino(bolsilloDestino);
                    tipoMovimiento = tipoMovimientoServices.getTipoMovimientoByCodes("CU","BO");
                    transaccionDto.setIdTipoMovimiento(tipoMovimiento);

                    // restar de mi cuenta y sumar al bolsillo destino
                    Double nuevoSaldoBolsillo = bolsilloDestino.getSaldo() + monto;
                    bolsilloDestino.setSaldo(nuevoSaldoBolsillo);

                    cuenta.setSaldoActual(saldoDisponible - monto);
                    cuenta.setFechaActualizacion(new Date());
                    cuentaServices.saveOrUpdateCuenta(cuenta);
                    bolsilloServices.saveOrUpdateBolsillo(bolsilloDestino);
                } else if(bolsilloOrigenId != null && bolsilloDestinoId != null) { // Transferir de un bolsillo a otro
                    bolsilloOrigen = bolsilloServices.getBolsilloById(bolsilloOrigenId);
                    bolsilloDestino = bolsilloServices.getBolsilloById(bolsilloDestinoId);
                    tipoMovimiento = tipoMovimientoServices.getTipoMovimientoByCodes("BO","BO");
                    transaccionDto.setIdTipoMovimiento(tipoMovimiento);

                    transaccionDto.setIdBolsilloOrigen(bolsilloOrigen);
                    transaccionDto.setIdBolsilloDestino(bolsilloDestino);
                    // restar del bolsillo origen y sumar al bolsillo destino
                    if(bolsilloOrigen.getSaldo() < monto) {
                        System.out.println("Rechazar transferencia");
                        return false;
                    }

                    bolsilloOrigen.setSaldo(bolsilloOrigen.getSaldo() - monto);
                    bolsilloDestino.setSaldo(bolsilloDestino.getSaldo() + monto);

                    bolsilloServices.saveOrUpdateBolsillo(bolsilloOrigen);
                    bolsilloServices.saveOrUpdateBolsillo(bolsilloDestino);
                } else if(bolsilloOrigenId != null && cuentaTerceros.isEmpty() && bolsilloDestinoId == null && transaccionDto.isMiCuentaDestino()) { // Transferir de un bolsillo a mi cuenta
                    bolsilloOrigen = bolsilloServices.getBolsilloById(bolsilloOrigenId);
                    transaccionDto.setIdBolsilloOrigen(bolsilloOrigen);
                    transaccionDto.setIdCuentaDestino(cuenta);
                    tipoMovimiento = tipoMovimientoServices.getTipoMovimientoByCodes("BO","CU");
                    transaccionDto.setIdTipoMovimiento(tipoMovimiento);

                    // sumar a mi cuenta y restar al bolsillo origen
                    if(bolsilloOrigen.getSaldo() < monto) {
                        System.out.println("Rechazar transferencia");
                        return false;
                    }

                    bolsilloOrigen.setSaldo(bolsilloOrigen.getSaldo() - monto);
                    cuenta.setSaldoActual(saldoDisponible + monto);
                    cuenta.setFechaActualizacion(new Date());
                    cuentaServices.saveOrUpdateCuenta(cuenta);
                    bolsilloServices.saveOrUpdateBolsillo(bolsilloOrigen);
                } else if(bolsilloOrigenId != null && !cuentaTerceros.isEmpty() && bolsilloDestinoId == null) { // Transferir de un bolsillo a una cuenta de terceros
                    bolsilloOrigen = bolsilloServices.getBolsilloById(bolsilloOrigenId);
                    tipoMovimiento = tipoMovimientoServices.getTipoMovimientoByCodes("BO","CU");
                    transaccionDto.setIdTipoMovimiento(tipoMovimiento);

                    transaccionDto.setIdBolsilloOrigen(bolsilloOrigen);
                    transaccionDto.setCuentaTerceros(cuentaTerceros);
                    // restar del bolsillo origen
                    if(bolsilloOrigen.getSaldo() < monto) {
                        System.out.println("Rechazar transferencia");
                        return false;
                    }

                    bolsilloOrigen.setSaldo(bolsilloOrigen.getSaldo() - monto);
                    bolsilloServices.saveOrUpdateBolsillo(bolsilloOrigen);
                }

            } else {
                boolean esDeposito = _tipoTransaccion.getNombre().equalsIgnoreCase("Deposito");
                boolean esRetiro = _tipoTransaccion.getNombre().equalsIgnoreCase("Retiro");

                if(esDeposito) {
                    cuenta.setSaldoActual(saldoDisponible + monto);
                }

                if (esRetiro){
                    cuenta.setSaldoActual(saldoDisponible - monto);
                }

                transaccionDto.setIdCuentaDestino(cuenta);
                cuenta.setFechaActualizacion(new Date());
                cuentaServices.saveOrUpdateCuenta(cuenta);
            }

            Transaccion transaccion = mapperTransaccion.TransaccionDtoToTransaccionDomain(transaccionDto);
            transaccionRepository.saveOrUpdateTransaccion(transaccion);

            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public List<Transaccion> getAllTransacciones() {
        return transaccionRepository.getAllTransacciones();
    }

    public List<Transaccion> getAllTransaccionesByUsuario(Integer usuarioId) {
        return transaccionRepository.getAllTransaccionesByUsuario(usuarioId);
    }

    public boolean deleteTransaccionById(Integer id){
        return transaccionRepository.deleteTransaccionById(id);
    }

    public Integer getCantidadTransacciones() {
        return transaccionRepository.getCantidadTransacciones();
    }

    public BigDecimal getTotalDineroTransacciones() {
        return transaccionRepository.getTotalDineroTransacciones();
    }

    public Integer getCantidadDepositos() {
        return transaccionRepository.getCantidadDepositos();
    }

    public Integer getCantidadRetiros() {
        return transaccionRepository.getCantidadRetiros();
    }

    public Integer getCantidadTransferencias() {
        return transaccionRepository.getCantidadTransferencias();
    }

    public BigDecimal getTotalDepositosByIdUsuario(Long usuarioId) {
        return transaccionRepository.getTotalDepositosByIdUsuario(usuarioId);
    }

    // Obtiene el total de retiros de un usuario
    public BigDecimal getTotalRetirosByIdUsuario(Long usuarioId) {
        return transaccionRepository.getTotalRetirosByIdUsuario(usuarioId);
    }

    public BigDecimal getTotalTransferenciasByIdUsuario(Long usuarioId) {
        return transaccionRepository.getTotalTransferenciasByIdUsuario(usuarioId);
    }

    public BigDecimal getBalanceNetoById(Long usuarioId) {
        return transaccionRepository.getBalanceNetoByIdUsuario(usuarioId);
    }


    public Transaccion getTransaccionById(Integer id) {
        return transaccionRepository.getTransaccionById(id);
    }

}
