package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.NombreUsuarioModel;

@Controller
public class Usuarios {

    @GetMapping("/usuarios")
    public String usuarios(Model model,HttpServletRequest request) {

        NombreUsuarioModel(model,request); // Se obtiene el nombre del usuario que inició sesión y lo guarda en el model


        return "users/users";
    }


}
