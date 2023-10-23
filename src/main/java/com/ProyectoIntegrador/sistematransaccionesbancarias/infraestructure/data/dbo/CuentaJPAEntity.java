package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Cuentas")
public class CuentaJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100, nullable = true)
    private String nombre;

    @Column(length = 50, nullable = false)
    private Date fechaCreacion;

    @Column(length = 50, nullable = true)
    private Date fechaActualizacion;
    @Column(length = 50, nullable = false)
    private Double saldo;

    @Column(length = 50, nullable = false)
    private Double saldoActual;
    private Double metaAhorro;
    @Column(length = 3, nullable = true)
    private Integer cvc;

    @Column(length = 50, nullable = false)
    private String tipoCuenta;

    @ManyToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id", nullable = false)
    private UsuarioJPAEntity usuarioId;

    public CuentaJPAEntity(){
    }

    public CuentaJPAEntity(Long id, String nombre, Date fechaCreacion, Date fechaActualizacion, Double saldo, Double saldoActual, Double metaAhorro, Integer cvc, String tipoCuenta, UsuarioJPAEntity usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.saldo = saldo;
        this.saldoActual = saldoActual;
        this.metaAhorro = metaAhorro;
        this.cvc = cvc;
        this.tipoCuenta = tipoCuenta;
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public UsuarioJPAEntity getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuarioJPAEntity usuarioId) {
        this.usuarioId = usuarioId;
    }
}
