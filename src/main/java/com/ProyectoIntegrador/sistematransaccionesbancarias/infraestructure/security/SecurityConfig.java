package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

// ? Esta clase se encarga de configurar la seguridad web en ña aplicación Spring Boot y Spring Security.

@Configuration// Indica que esta clase es de configuración para Spring Boot y Spring Security y tiene metodos anotados con @Bean que retornan objetos que seran administrados por el contenedor de Spring
@EnableWebSecurity// Habilita la seguridad web en la aplicación
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;  // Copia virtual de la base de datos para poder hacer consultas y no ir directamente a la base de datos, esto es más eficiente


    // Cuando el usuario intente iniciar sesión se debe hacer una consulta a la base de datos para verificar que el usuario exista
    // y que la contraseña sea la correcta, para esto se debe crear un objeto de tipo JdbcUserDetailsManager que es el que se encarga de hacer la consulta a la base de datos
    // y verificar que el usuario exista y que la contraseña sea la correcta
    // Para crear el objeto JdbcUserDetailsManager se debe crear un objeto de tipo DataSource que es el que se encarga de hacer la conexión con la base de datos

    //? Validar la existencia del usuario, codificar la contraseña y verificar que coincida con la contraseña encriptada de la base de datos ( hace la autenticacion de los usuarios)

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        try{
            auth.jdbcAuthentication() //  configura la autenticación para que utilice una base de datos a través de JDBC, lo que permitirá autenticar a los usuarios utilizando una tabla de usuarios almacenada en la base de datos.

                    .dataSource(dataSource) // Se le pasa el objeto DataSource para que pueda hacer la conexión con la base de datos,  Se establece el DataSource que se utilizará para la autenticación

                    .usersByUsernameQuery("SELECT usuarios.id,usuarios.contrasena,estados.nombre FROM usuarios JOIN estados ON usuarios.estado_id = estados.id  WHERE usuarios.id=?") // : Se especifica la consulta SQL que se utilizará para obtener los detalles del usuario basado en id del usiarop

                    .authoritiesByUsernameQuery("SELECT usuarios.id,roles.nombre FROM sistemabanca.usuarios JOIN roles ON usuarios.rol_id = roles.id  WHERE usuarios.id=?") // Se especifica la consulta SQL que se utilizará para obtener los roles/autoridades del usuario basado en el  usuario proporcionado

                    .passwordEncoder(new BCryptPasswordEncoder()); // Se establece el codificador de contraseñas que se utilizará para codificar y verificar las contraseñas de los usuarios
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error en la consulta de usuarios");
        }
    }




}
