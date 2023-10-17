package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "TipoMovimiento")
public class TipoMovimientoJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column(length = 45, nullable = false)
    private String codigoOrigen;

    @Column(length = 45, nullable = false)
    private String codigoDestino;

    @Column(length = 250, nullable = false)
    private String descripcion;

    public TipoMovimientoJPAEntity() {}

    public TipoMovimientoJPAEntity(Integer id, String codigoOrigen, String codigoDestino, String descripcion) {
        Id = id;
        this.codigoOrigen = codigoOrigen;
        this.codigoDestino = codigoDestino;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCodigoOrigen() {
        return codigoOrigen;
    }

    public void setCodigoOrigen(String codigoOrigen) {
        this.codigoOrigen = codigoOrigen;
    }

    public String getCodigoDestino() {
        return codigoDestino;
    }

    public void setCodigoDestino(String codigoDestino) {
        this.codigoDestino = codigoDestino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
