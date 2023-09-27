package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class RolJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer ID; // ? No se puede cambiar el id de un rol
    @Column(length = 50, nullable = false)
    public String nombre;

    public RolJPAEntity() {
    }

    public RolJPAEntity(Integer ID, String nombre) {
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

}
