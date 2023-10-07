package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Clase para el manejo de la respuesta de la autenticación
public class AuthResponse {
    String token;
    String message;
}
