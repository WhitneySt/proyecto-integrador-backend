package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.InformationUsuarioModel;

@Controller
public class EstadisticaController {


    @GetMapping("/estadistica")
    public String estadistica(HttpServletRequest request, Model model){

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        InformationUsuarioModel(model,request); // Se obtiene el nombre  yla imgdel usuario que inició sesión y lo guarda eel model

        model.addAttribute("usuarioRol",usuarioLogeado.getRol().getNombre()); // Se guarda el usuario en el model

        return "/estadistica";
    }

}
