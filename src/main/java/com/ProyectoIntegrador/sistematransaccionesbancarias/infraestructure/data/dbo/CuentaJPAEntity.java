package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Cuentas")
public class CuentaJPAEntity {

    @Id
    @Column(length = 12, nullable = false)
    private Long id;
    @Column(length = 100, nullable = true)
    private String nombre;

    @Column(length = 50, nullable = false)
    private Date fechaCreacion;
    @Column(length = 50, nullable = false)
    private Double saldo;
    private Double metaAhorro;
    @Column(length = 3, nullable = false)
    private Integer cvc;

    @Column(length = 50, nullable = false)
    private String tipoCuenta;

    @ManyToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id", nullable = false)
    private UsuarioJPAEntity usuarioId;

    public CuentaJPAEntity(){
    }

    public CuentaJPAEntity( Long id, Date fechaCreacion, Double saldo, Double metaAhorro, String tipoCuenta,Integer cvc,UsuarioJPAEntity usuarioId) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
        this.metaAhorro = metaAhorro;
        this.tipoCuenta = tipoCuenta;
        this.cvc = cvc;
        this.usuarioId = usuarioId;
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

    public UsuarioJPAEntity getusuarioId() {
        return usuarioId;
    }

    public void setusuarioId(UsuarioJPAEntity usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

}
