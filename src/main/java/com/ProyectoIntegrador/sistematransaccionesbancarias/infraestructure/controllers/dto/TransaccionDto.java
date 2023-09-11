package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto;

import java.sql.Date;

public class TransaccionDto {
    private Integer id;
    private Date fechaTransaccion;
    private Double monto;
    private String descripcion;
    private Integer idTipoTransaccion;
    private Integer idCuentaOrigen;
    private Integer idCuentaDestino;

    private Integer idBolsilloOrigen;
    private Integer idBolsilloDestino;
    private Integer idTipoMovimiento;

    public TransaccionDto(){}

    public TransaccionDto(Integer id, Date fechaTransaccion, Double monto, String descripcion, Integer idTipoTransaccion, Integer idCuentaOrigen, Integer idCuentaDestino, Integer idBolsilloOrigen, Integer idBolsilloDestino, Integer idTipoMovimiento) {
        this.id = id;
        this.fechaTransaccion = fechaTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
        this.idTipoTransaccion = idTipoTransaccion;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
        this.idBolsilloOrigen = idBolsilloOrigen;
        this.idBolsilloDestino = idBolsilloDestino;
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(Integer idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public Integer getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(Integer idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public Integer getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(Integer idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public Integer getIdBolsilloOrigen() {
        return idBolsilloOrigen;
    }

    public void setIdBolsilloOrigen(Integer idBolsilloOrigen) {
        this.idBolsilloOrigen = idBolsilloOrigen;
    }

    public Integer getIdBolsilloDestino() {
        return idBolsilloDestino;
    }

    public void setIdBolsilloDestino(Integer idBolsilloDestino) {
        this.idBolsilloDestino = idBolsilloDestino;
    }

    public Integer getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }
}
