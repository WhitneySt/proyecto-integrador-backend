package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;

import java.sql.Date;

public class Cuenta {
    private String nombre;
    private Long id;
    private Date fechaCreacion;
    private Double saldo;
    private Double metaAhorro;
    private Integer cvc;

    private String tipoCuenta;
    private Usuario usuarioId;

    public Cuenta(){
    }

    public Cuenta( String nombre, Long id, Date fechaCreacion, Double saldo, Double metaAhorro, Integer cvc,String tipoCuenta, Usuario usuarioId) {
        this.nombre = nombre;
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getMetaAhorro() {
        return metaAhorro;
    }

    public void setMetaAhorro(Double metaAhorro) {
        this.metaAhorro = metaAhorro;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }


    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }
}