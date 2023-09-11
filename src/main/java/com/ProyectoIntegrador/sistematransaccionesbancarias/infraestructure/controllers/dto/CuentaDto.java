package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;

import java.sql.Date;

public class CuentaDto {

    private Integer id;

    private String nombre;
    private String numeroCuenta;
    private Date fechaCreacion;
    private Double saldo;
    private Double metaAhorro;

    private String color;
    private Usuario usuarioId;

    public CuentaDto(){
        this.fechaCreacion = new Date(System.currentTimeMillis());
    }

    public CuentaDto(Integer id, String nombre, String numeroCuenta, Date fechaCreacion, Double saldo, Double metaAhorro, String color, Usuario usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
        this.metaAhorro = metaAhorro;
        this.color = color;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }
}
