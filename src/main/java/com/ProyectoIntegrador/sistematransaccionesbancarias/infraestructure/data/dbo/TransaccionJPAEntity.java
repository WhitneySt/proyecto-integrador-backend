package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table(name = "Transaccion")
public class TransaccionJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    @Column(length = 50, nullable = false)
    @CreationTimestamp
    private Date fechaTransaccion;

    @Column(length = 50, nullable = false)
    private Double monto;

    @Column(length = 250, nullable = true)
    private String descripcion;

    @Column(length = 150, nullable = true)
    private String cuentaTerceros;

    @ManyToOne
    @JoinColumn(name = "idTipoTransaccion", referencedColumnName = "id", nullable = false)
    private TipoTransaccionJPAEntity idTipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "idCuentaOrigen",referencedColumnName = "id", nullable = true)
    private CuentaJPAEntity idCuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "idCuentaDestino",referencedColumnName = "id", nullable = true)
    private CuentaJPAEntity idCuentaDestino;

    @ManyToOne
    @JoinColumn(name = "idBolsilloOrigen",referencedColumnName = "id", nullable = true)
    private BolsilloJPAEntity idBolsilloOrigen;

    @ManyToOne
    @JoinColumn(name = "idBolsilloDestino",referencedColumnName = "id", nullable = true)
    private BolsilloJPAEntity idBolsilloDestino;

    @ManyToOne
    @JoinColumn(name = "idTipoMovimiento", referencedColumnName = "id", nullable = true)
    private TipoMovimientoJPAEntity idTipoMovimiento;

    @ManyToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id", nullable = false)
    private UsuarioJPAEntity usuarioId;

    public TransaccionJPAEntity(){}

    public TransaccionJPAEntity(Integer id, Date fechaTransaccion, Double monto, String descripcion, String cuentaTerceros, TipoTransaccionJPAEntity idTipoTransaccion, CuentaJPAEntity idCuentaOrigen, CuentaJPAEntity idCuentaDestino, BolsilloJPAEntity idBolsilloOrigen, BolsilloJPAEntity idBolsilloDestino, TipoMovimientoJPAEntity idTipoMovimiento, UsuarioJPAEntity usuarioId) {
        Id = id;
        this.fechaTransaccion = fechaTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaTerceros = cuentaTerceros;
        this.idTipoTransaccion = idTipoTransaccion;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
        this.idBolsilloOrigen = idBolsilloOrigen;
        this.idBolsilloDestino = idBolsilloDestino;
        this.idTipoMovimiento = idTipoMovimiento;
        this.usuarioId = usuarioId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getCuentaTerceros() {
        return cuentaTerceros;
    }

    public void setCuentaTerceros(String cuentaTerceros) {
        this.cuentaTerceros = cuentaTerceros;
    }

    public TipoTransaccionJPAEntity getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(TipoTransaccionJPAEntity idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public CuentaJPAEntity getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(CuentaJPAEntity idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public CuentaJPAEntity getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(CuentaJPAEntity idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public BolsilloJPAEntity getIdBolsilloOrigen() {
        return idBolsilloOrigen;
    }

    public void setIdBolsilloOrigen(BolsilloJPAEntity idBolsilloOrigen) {
        this.idBolsilloOrigen = idBolsilloOrigen;
    }

    public BolsilloJPAEntity getIdBolsilloDestino() {
        return idBolsilloDestino;
    }

    public void setIdBolsilloDestino(BolsilloJPAEntity idBolsilloDestino) {
        this.idBolsilloDestino = idBolsilloDestino;
    }

    public TipoMovimientoJPAEntity getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(TipoMovimientoJPAEntity idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public UsuarioJPAEntity getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuarioJPAEntity usuarioId) {
        this.usuarioId = usuarioId;
    }
}
