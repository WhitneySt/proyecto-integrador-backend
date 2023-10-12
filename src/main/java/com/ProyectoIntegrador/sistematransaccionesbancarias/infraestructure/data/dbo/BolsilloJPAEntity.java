package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import jakarta.persistence.*;

@Entity
@Table(name = "Bolsillo")
public class BolsilloJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 150, nullable = false)
    private String nombre;

    private Double saldo;

    @Column(length = 40, nullable = false)
    private String color;
    @ManyToOne
    @JoinColumn(name = "idCuenta", referencedColumnName = "id", nullable = false)
    private CuentaJPAEntity idCuenta;

    BolsilloJPAEntity(){}

    public BolsilloJPAEntity(Integer id, String nombre, Double saldo, String color, CuentaJPAEntity idCuenta) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.color = color;
        this.idCuenta = idCuenta;
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CuentaJPAEntity getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(CuentaJPAEntity idCuenta) {
        this.idCuenta = idCuenta;
    }
}
