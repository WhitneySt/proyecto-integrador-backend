package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities;

public class Bolsillo {
    private Integer id;
    private String nombre;
    private Double saldo;
    private String color;
    private Cuenta idCuenta;

    public Bolsillo(){}

    public Bolsillo(Integer id, String nombre, Double saldo, String color, Cuenta idCuenta) {
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

    public Cuenta getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuenta idCuenta) {
        this.idCuenta = idCuenta;
    }
}
