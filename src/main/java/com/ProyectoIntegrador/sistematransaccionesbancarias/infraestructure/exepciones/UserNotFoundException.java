package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
