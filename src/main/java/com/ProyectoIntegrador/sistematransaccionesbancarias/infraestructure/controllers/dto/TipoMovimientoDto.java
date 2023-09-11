package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto;

public class TipoMovimientoDto {
    private Integer id;
    private String codigoOrigen;
    private String codigoDestino;
    private String descripcion;

    public TipoMovimientoDto(){}

    public TipoMovimientoDto(Integer id, String codigoOrigen, String codigoDestino, String descripcion) {
        this.id = id;
        this.codigoOrigen = codigoOrigen;
        this.codigoDestino = codigoDestino;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
