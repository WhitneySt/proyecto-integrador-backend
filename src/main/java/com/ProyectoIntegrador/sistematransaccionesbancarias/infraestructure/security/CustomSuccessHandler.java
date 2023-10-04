package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component // indica que esto debe verificarce al inicio del proceso (despues de la autenticacion)
//? Esta clase se encarga de manejar el éxito de la autenticación es decir que  encarga de especificar que hacer despues de que un usuario inicie sesión
public class CustomSuccessHandler   extends SimpleUrlAuthenticationSuccessHandler {

    // En el caso de que el usuario se autentique correctamente
    // Se  indica  que se debe hacer despues del login exitoso

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy(); // Se crea un objeto de tipo RedirectStrategy que se encargará de redirigir al usuario a la página que se especifique

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication); // Se obtiene la URL a la que se redirigirá al usuario
        if (response.isCommitted()) { // Si la respuesta ya ha sido enviada al cliente, se termina la ejecución del método
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl); // Se redirige al usuario a la URL especificada
    }

    // Se establece la URL a la que se redirigirá al usuario dependiendo del tipo de rol que tenga
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); // Se obtienen los roles que tiene el usuario y los pasa  a una lista, esto se trajo de la base de datos en setConfig

        List<String> roles = new ArrayList<String>();  //Crea una  lista para guardar  los roles

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority()); // guarda los tipos de roles que hay en la lista roles
        }

        // Dependiendo del tipo de rol que tenga el usuario se redirige a una página u otra
        if (esAdministrativo(roles)) {
            url = "/usuarios";
        } else if (esUsuario(roles)) {
            url = "/";
        }
        // si no tiene permisos el usuario
        else {
            url = "/Denegado";
        }
        return url;
    }


    // Metodos para verificar que tipo de rol es el usuario
    // itera los roles que hay en la  lista roles y verifica que tipo de rol es el usuario
    private boolean esUsuario(List<String> roles) {
        if (roles.contains("Usuario")) {
            System.out.println("es usuario");
            return true;
        }
        return false;
    }

    private boolean esAdministrativo(List<String> roles) {
        if (roles.contains("Administrador")) {
            System.out.println("es administrador");
            return true;
        }
        return false;


    }

}
