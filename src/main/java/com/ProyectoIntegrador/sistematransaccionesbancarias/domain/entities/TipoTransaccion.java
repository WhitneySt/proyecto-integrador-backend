package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;

public class TipoTransaccion {
    private Integer id;
    private String nombre;

    public TipoTransaccion(){}

    public TipoTransaccion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
