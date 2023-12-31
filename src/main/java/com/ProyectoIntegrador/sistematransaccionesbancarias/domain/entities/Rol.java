package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;

public class Rol {

    private Integer ID; // ? No se puede cambiar el id de un rol
    private String nombre;

    public Rol() {
    }

    public Rol(Integer ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
