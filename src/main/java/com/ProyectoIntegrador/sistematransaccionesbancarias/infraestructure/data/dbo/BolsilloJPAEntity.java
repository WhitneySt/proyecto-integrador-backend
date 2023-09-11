package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "Bolsillo")
public class BolsilloJPAEntity {
    @Id
    private Integer id;
    @Column(length = 150, nullable = false)
    private String nombre;

    private Double saldo;

    @Column(length = 40, nullable = false)
    private String color;
    @ManyToOne
    @JoinColumn(name = "idCuenta", nullable = false)
    private CuentaJPAEntity idCuenta;
}
