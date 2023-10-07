package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security.Auth;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.EstadoJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.RolJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioJPARepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

/*    public AuthResponse login(Usuario usuario) {

        try {
            UserDetails user = userRepository.findById(usuario.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); // Obtener el usuario

            String token = jwtService.getToken(user); // Obtener el token

            // Si el token es null, retorna un AuthResponse con el token null y un mensaje de error
            if (token == null) {
                System.out.println("Error al iniciar sesión");
                return AuthResponse.builder()
                        .token(null)
                        .message("Error al iniciar sesión")
                        .build();
            } else {
                // Realiza la autenticación solo si el token no es null
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getId(), usuario.getContrasena()));
                System.out.println("Inicio de sesión exitoso");
                return AuthResponse.builder()
                        .token(token)
                        .message("Inicio de sesión exitoso")
                        .build();
            }
        } catch (UsernameNotFoundException e) {
            // Manejo de excepción si el usuario no se encuentra en la base de datos
            System.out.println("Usuario no encontrado: " + e.getMessage());
            return AuthResponse.builder()
                    .token(null)
                    .message("Usuario no encontrado")
                    .build();
        }

    }*/

    public AuthResponse register(Usuario usuario) {
        // Creal el objeto
        UsuarioJPAEntity user = UsuarioJPAEntity.builder()
                        .nombre(usuario.getNombre())
                        .correo(usuario.getCorreo())
                        .id(usuario.getId())
                        .contrasena(passwordEncoder.encode( usuario.getContrasena()))
                        .rol(new RolJPAEntity(1, "Usuario"))
                        .estado(new EstadoJPAEntity(1, true))
                        .fechaCreacion(new Date(System.currentTimeMillis()))
                        .build();

        // valida si el usuario ya existe
        Boolean userExists = userRepository.findById(usuario.getId()).isPresent();
        if (userExists){
            System.out.println("El usuario ya existe");
            // retorna un AuthResponse con el token null y un mensaje de error
            return AuthResponse.builder()
                    .token(null)
                    .message("El usuario ya existe")
                    .build();
        }

        userRepository.save(user);
        return AuthResponse.builder() // retorna un AuthResponse con el token y un mensaje de exito
                .token(jwtService.getToken(user))
                .message("Usuario creado con exito")
                .build();

    }
}
