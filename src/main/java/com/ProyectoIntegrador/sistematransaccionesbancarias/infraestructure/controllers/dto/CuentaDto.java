package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;

import java.sql.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CuentaDto {

    private String nombre;
    private Long id;
    private Date fechaCreacion;
    private Double saldo;
    private Double metaAhorro;
    private Integer cvc;

    private String tipoCuenta;
    private Usuario usuarioId;

    public CuentaDto(){
        this.fechaCreacion = new Date(System.currentTimeMillis());
        this.id = generarid();
        this.cvc = generarCvc();

    }

    public CuentaDto(String nombre, Long id, Date fechaCreacion, Double saldo, Double metaAhorro, Integer cvc,String tipoCuenta, Usuario usuarioId) {
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

    public Long generarid() {
        Long id = ThreadLocalRandom.current().nextLong(100000000000L, 1000000000000L);
        return id;
    }

    public int generarCvc() {
        Random random = new Random();
        int cvc = random.nextInt(900) + 100; // Genera un número aleatorio entre 100 y 999
        return cvc;
    }

    @Override
    public String toString() {
        return "CuentaDto{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", saldo=" + saldo +
                ", metaAhorro=" + metaAhorro +
                ", cvc=" + cvc +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
