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
                .info(new Info().title("Título de la API")
                        .description("Descripción de la API")
                        .version("Versión de la API")
                        .license(new License().name("Nombre de la licencia").url("URL de la licencia")))
                .externalDocs(new ExternalDocumentation()
                        .description("Descripción de la documentación externa")
                        .url("URL de la documentación externa"));
    }
}