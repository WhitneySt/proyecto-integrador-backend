package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estados")
public class EstadoJPAEntity {

    @Id
    private Integer ID; // ? No se puede cambiar el id de un estado
    @Column(nullable = false)
    private Boolean nombre;

    public EstadoJPAEntity() {
    }

    public EstadoJPAEntity(Integer ID, Boolean nombre) {
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
