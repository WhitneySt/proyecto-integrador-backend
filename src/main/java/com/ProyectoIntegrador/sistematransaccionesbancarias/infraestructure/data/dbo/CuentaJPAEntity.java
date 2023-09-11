package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Cuentas")
public class CuentaJPAEntity {

    @Id
    private Integer id;
    @Column(length = 100, nullable = false)
    private String nombre;
    @Column(length = 50, nullable = false)
    private String numeroCuenta;
    @Column(length = 50, nullable = false)
    private Date fechaCreacion;
    @Column(length = 50, nullable = false)
    private Double saldo;
    private Double metaAhorro;

    @Column(length = 100, nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id", nullable = false)
    private UsuarioJPAEntity usuarioId;

    public CuentaJPAEntity(){
    }

    public CuentaJPAEntity(Integer id, String numeroCuenta, Date fechaCreacion, Double saldo, Double metaAhorro, UsuarioJPAEntity usuarioId) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
        this.metaAhorro = metaAhorro;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UsuarioJPAEntity getusuarioId() {
        return usuarioId;
    }

    public void setusuarioId(UsuarioJPAEntity usuarioId) {
        this.usuarioId = usuarioId;
    }


}
