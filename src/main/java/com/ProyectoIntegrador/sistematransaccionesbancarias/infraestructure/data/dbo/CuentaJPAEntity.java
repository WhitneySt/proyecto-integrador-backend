package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;

public class CuentaJPAEntity {

    @Id
    private Integer id;
    @Column(length = 50, nullable = false)
    private String numeroCuenta;
    @Column(length = 50, nullable = false)
    private Date fechaCreacion;
    @Column(length = 50, nullable = false)
    private Double saldo;
    private Double metaAhorro;

    @OneToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id", nullable = false)
    private Usuario usuarioId;

    public CuentaJPAEntity(){
    }

    public CuentaJPAEntity(Integer id, String numeroCuenta, Date fechaCreacion, Double saldo, Double metaAhorro, Usuario usuarioId) {
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

    public Usuario getusuarioId() {
        return usuarioId;
    }

    public void setusuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }


}
