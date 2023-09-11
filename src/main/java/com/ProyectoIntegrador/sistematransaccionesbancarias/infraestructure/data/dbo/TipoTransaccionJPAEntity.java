package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "TipoTrasanccion")
public class TipoTransaccionJPAEntity {
    @Id
    private Integer Id;

    @Column(length = 250, nullable = false)
    private String descripcion;
}
