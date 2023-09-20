package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones;


public class CuentaNotFoundException extends RuntimeException {
    public CuentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}