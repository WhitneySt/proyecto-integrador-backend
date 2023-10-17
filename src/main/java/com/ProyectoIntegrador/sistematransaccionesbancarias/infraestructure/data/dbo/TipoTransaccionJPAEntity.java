package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.TipoTransaccion;
import jakarta.persistence.*;

@Entity
@Table(name = "TipoTransaccion")
public class TipoTransaccionJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 250, nullable = false)
    private String nombre;

    public TipoTransaccionJPAEntity(){}

    public TipoTransaccionJPAEntity(Integer id, String nombre) {
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
