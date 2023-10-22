package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.*;

import java.util.Date;

public class TransaccionDto {
    private Integer id;
    private Date fechaTransaccion;
    private Double monto;
    private String descripcion;
    private TipoTransaccion idTipoTransaccion;
    private Cuenta idCuentaOrigen;
    private Cuenta idCuentaDestino;

    private Bolsillo idBolsilloOrigen;
    private Bolsillo idBolsilloDestino;
    private TipoMovimiento idTipoMovimiento;

    private Usuario usuarioId;

    private String cuentaTerceros;
    private Integer bolsilloOrigenId;
    private Integer bolsilloDestinoId;

    public TransaccionDto(){}

    public TransaccionDto(Integer id, Date fechaTransaccion, Double monto, String descripcion, TipoTransaccion idTipoTransaccion, Cuenta idCuentaOrigen, Cuenta idCuentaDestino, Bolsillo idBolsilloOrigen, Bolsillo idBolsilloDestino, TipoMovimiento idTipoMovimiento, Usuario usuarioId, String cuentaTerceros, Integer bolsilloOrigenId, Integer bolsilloDestinoId) {
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
        this.usuarioId = usuarioId;
        this.cuentaTerceros = cuentaTerceros;
        this.bolsilloOrigenId = bolsilloOrigenId;
        this.bolsilloDestinoId = bolsilloDestinoId;
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

    public TipoTransaccion getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(TipoTransaccion idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public Cuenta getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(Cuenta idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public Cuenta getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(Cuenta idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public Bolsillo getIdBolsilloOrigen() {
        return idBolsilloOrigen;
    }

    public void setIdBolsilloOrigen(Bolsillo idBolsilloOrigen) {
        this.idBolsilloOrigen = idBolsilloOrigen;
    }

    public Bolsillo getIdBolsilloDestino() {
        return idBolsilloDestino;
    }

    public void setIdBolsilloDestino(Bolsillo idBolsilloDestino) {
        this.idBolsilloDestino = idBolsilloDestino;
    }

    public TipoMovimiento getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(TipoMovimiento idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCuentaTerceros() {
        return cuentaTerceros;
    }

    public void setCuentaTerceros(String cuentaTerceros) {
        this.cuentaTerceros = cuentaTerceros;
    }

    public Integer getBolsilloOrigenId() {
        return bolsilloOrigenId;
    }

    public void setBolsilloOrigenId(Integer bolsilloOrigenId) {
        this.bolsilloOrigenId = bolsilloOrigenId;
    }

    public Integer getBolsilloDestinoId() {
        return bolsilloDestinoId;
    }

    public void setBolsilloDestinoId(Integer bolsilloDestinoId) {
        this.bolsilloDestinoId = bolsilloDestinoId;
    }

    @Override
    public String toString() {
        return "TransaccionDto{" +
                "id=" + id +
                ", fechaTransaccion=" + fechaTransaccion +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", idTipoTransaccion=" + idTipoTransaccion +
                ", idCuentaOrigen=" + idCuentaOrigen +
                ", idCuentaDestino=" + idCuentaDestino +
                ", idBolsilloOrigen=" + idBolsilloOrigen +
                ", idBolsilloDestino=" + idBolsilloDestino +
                ", idTipoMovimiento=" + idTipoMovimiento +
                ", usuarioId=" + usuarioId +
                ", cuentaTerceros=" + cuentaTerceros +
                ", bolsilloOrigenId=" + bolsilloOrigenId +
                ", bolsilloDestinoId=" + bolsilloDestinoId +
                '}';
    }
}
