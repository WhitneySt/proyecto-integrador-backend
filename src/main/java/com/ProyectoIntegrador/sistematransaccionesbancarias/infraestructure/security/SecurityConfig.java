package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

// ? Esta clase se encarga de configurar la seguridad web en ña aplicación Spring Boot y Spring Security.

@Configuration// Indica que esta clase es de configuración para Spring Boot y Spring Security y tiene metodos anotados con @Bean que retornan objetos que seran administrados por el contenedor de Spring
@EnableWebSecurity// Habilita la seguridad web en la aplicación
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;  // Copia virtual de la base de datos para poder hacer consultas y no ir directamente a la base de datos, esto es más eficiente

    // Cuando el usuario intente iniciar sesión se debe hacer una consulta a la base de datos para verificar que el usuario exista

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

    @Bean // Permite que el objeto retornado por el método se administre por el contenedor de Spring
    public SecurityFilterChain securiryFilterChain (HttpSecurity http ) throws Exception {

        return http // retorna un objeto SecurityFilterChain que se encarga de configurar la seguridad web en la aplicación
                .csrf(csrf-> csrf.disable()) // Deshabilita la protección CSRF

                // Autorizacion de los endpoints
                .authorizeHttpRequests(authRaquests ->
                                authRaquests
                                        .requestMatchers("/login","/registro").permitAll() // los endpoints que empiecen con  /registro y login son publicos y no requieren autenticacion
                                        .requestMatchers("/home","/").authenticated() // los endpoints que empiecen con /home requieren autenticacion
                                        .requestMatchers("/usuarios","/detalleUsuario/**").authenticated() // los endpoints que empiecen con /usuarios y /detalleUsuario requieren autenticacion
                                        .requestMatchers("/editarPerfi").authenticated() // los endpoints que empiecen con /editarPerfil requieren autenticacion
                                        .requestMatchers("/editarPerfil","/perfil").authenticated() // los endpoints que empiecen con /editarPerfil y /perfil requieren autenticacion
                                        .anyRequest().permitAll() // cualquier otra ruta es publica y no requiere autenticacion
                )

                //.formLogin(Customizer.withDefaults()) // el login por defecto es el que viene por defecto de spring
                .formLogin(formLogin -> // Se configura el formulario de inicio de sesión personalizado
                        formLogin
                                .loginPage("/login") // Se especifica la ruta del formulario de inicio de sesión personalizado
                                .permitAll() // Se permite el acceso a todos los usuarios
                                .defaultSuccessUrl("/home", true) // Se especifica la ruta a la que se redirigirá al usuario después de iniciar sesión correctamente

                                //.failureUrl("/login?error=true") // Se especifica la ruta a la que se redirigirá al usuario después de iniciar sesión incorrectamente
                                .failureHandler((request, response, exception) -> {
                                    // en el caso de que el usuario este deshabilitado
                                    if (exception instanceof DisabledException) { // si la excepcion es de tipo DisabledException es porque el usuario esta deshabilitado
                                        request.getSession().setAttribute("mensaje", "Usuario deshabilitado"); //  establece un atributo llamado "mensaje" en la sesión de la solicitud actual.
                                        response.sendRedirect("/login?deshabilitado=true");
                                    }
                                    // si hay algun error aparece esta url, y desde la plantilla se genera un error
                                    else {
                                        //request.getSession().setAttribute("mensaje", "Credenciales de inicio de sesión incorrectas"); // se comentó para no enviarla a la plantilla y ver otra forma de generar mensajes por medio de la url
                                        response.sendRedirect("/login?error=true");
                                    }
                                })
                                // estos dos son los campos que se deben enviar en el formulario de login, por eso debe tener el nombre del input de cada uno
                                .usernameParameter("id") // Se especifica el nombre del parámetro que se utilizará para obtener el nombre de usuario
                                .passwordParameter("contrasena") // Se especifica el nombre del parámetro que se utilizará para obtener la contraseña

                                // TODO Habilitar cuando se haga autoriazacion por roles
                                // .successHandler((AuthenticationSuccessHandler) customSuccessHandler) // se envia el manejador de exito para que se ejecute cuando se inicie sesion correctamente para que se haga la redireccion dependiendo del rol
                )

                .logout(logout -> // Se configura el formulario de cierre de sesión personalizado
                        logout
                                .logoutUrl("/logout") // Se especifica la ruta del formulario de cierre de sesión personalizado
                                .logoutSuccessUrl("/login") // Se especifica la ruta a la que se redirigirá al usuario después de cerrar sesión correctamente
                                .permitAll() // Se permite el acceso a todos los usuarios
                                .invalidateHttpSession(true) // invalida la sesion
                                .deleteCookies("JSESSIONID") // elimina la cookie de la sesion
                )



                .build(); // Se construye el objeto SecurityFilterChain


    }


}

