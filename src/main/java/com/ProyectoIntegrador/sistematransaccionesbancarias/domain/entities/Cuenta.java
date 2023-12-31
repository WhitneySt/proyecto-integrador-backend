package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;


import java.util.Date;

public class Cuenta {
    private String nombre;
    private Long id;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Double saldo;
    private Double saldoActual;
    private Double metaAhorro;
    private Integer cvc;

    private String tipoCuenta;
    private Usuario usuarioId;

    public Cuenta(){
    }

    public Cuenta(String nombre, Long id, Date fechaCreacion, Date fechaActualizacion, Double saldo, Double saldoActual, Double metaAhorro, Integer cvc, String tipoCuenta, Usuario usuarioId) {
        this.nombre = nombre;
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.saldo = saldo;
        this.saldoActual = saldoActual;
        this.metaAhorro = metaAhorro;
        this.cvc = cvc;
        this.tipoCuenta = tipoCuenta;
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Double getMetaAhorro() {
        return metaAhorro;
    }

    public void setMetaAhorro(Double metaAhorro) {
        this.metaAhorro = metaAhorro;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", saldo=" + saldo +
                ", saldoActual=" + saldoActual +
                ", metaAhorro=" + metaAhorro +
                ", cvc=" + cvc +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", usuarioId=" + usuarioId +
                '}';
    }
}