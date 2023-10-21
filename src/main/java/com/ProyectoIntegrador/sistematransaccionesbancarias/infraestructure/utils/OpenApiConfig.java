package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.utils;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI awesomeAPI() {
        return new OpenAPI()
                .info(new Info().title("Sistema de Transacciones Bancarias Makaia  API")
                        .description("Este es el backend del Sistema de Transacciones Bancarias, que proporciona servicios para administrar cuentas, bolsillos y transacciones. La API incluye funcionalidades como registro, inicio de sesión, gestión de usuarios para administradores y gestión de perfile para usuarios. También ofrece estadísticas financieras para un mejor control de tus transacciones. Ofrece autenticación y autorización por roles, con usuarios y administradores.")
                        .version("1.0.0")
                        .license(new License().name("Licencia MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio de GitHub")
                        .url("https://github.com/WhitneySt/proyecto-integrador-backend/tree/main"));
    }
}