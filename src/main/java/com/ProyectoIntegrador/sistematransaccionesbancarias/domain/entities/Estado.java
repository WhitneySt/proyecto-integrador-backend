package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;

public class Estado {

    private Integer ID; // ? No se puede cambiar el id de un estado
    private Boolean nombre;

    public Estado() {
    }

    public Estado(Integer ID, Boolean nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public Integer getID() {
        return ID;
    }

    public  void setID(Integer ID) {
        this.ID = ID;
    }

    public Boolean getNombre() {
        return nombre;
    }

    public void setNombre(Boolean Nombre) {
        this.nombre = Nombre;
    }

}
