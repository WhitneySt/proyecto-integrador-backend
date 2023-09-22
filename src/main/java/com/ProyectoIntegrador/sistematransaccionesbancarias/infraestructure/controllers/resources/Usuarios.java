package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.NombreUsuarioModel;

@Controller
public class Usuarios {

    @Autowired
    MapperUsuario mapperUsuario;
    UsuarioService usuarioServices;
    UsuarioImplementacion repository;

    @Autowired
    public Usuarios(UsuarioJPARepository usuarioJPARepository,MapperUsuario mapperUsuario){
        this.repository = new UsuarioImplementacion(usuarioJPARepository,mapperUsuario);
        this.usuarioServices = new UsuarioService(this.repository);
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model,HttpServletRequest request) {

        NombreUsuarioModel(model,request); // Se obtiene el nombre del usuario que inició sesión y lo guarda en el model

        List<Usuario> listaUsuarios = usuarioServices.getAllUsuarios();
        model.addAttribute("usuarios",listaUsuarios);

        return "users/users";
    }

    @GetMapping("/detalleUsuario")
    public String verDetalle(Model model, HttpServletRequest request) {


        return "users/informationUser";
    }


}
