package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;

public class Estado {

    private Integer ID; // ? No se puede cambiar el id de un estado
    private String nombre;

    public Estado() {
    }

    public Estado(Integer ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public Integer getID() {
        return ID;
    }

    public  void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

}
