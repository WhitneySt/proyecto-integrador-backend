package com.ProyectoIntegrador.sistematransaccionesbancarias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class SistemaTransaccionesBancariasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaTransaccionesBancariasApplication.class, args);
		System.out.println("Aplicación ejecutada");

	}

}
