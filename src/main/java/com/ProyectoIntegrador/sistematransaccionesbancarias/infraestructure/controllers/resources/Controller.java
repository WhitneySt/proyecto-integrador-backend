package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private final MapperUsuario mapperUsuario;
    UsuarioService usuarioService;
    UsuarioImplementacion repository;

    @Autowired
    public Controller(UsuarioJPARepository jpaRepository, MapperUsuario mapperUsuario) {
        this.repository = new UsuarioImplementacion(jpaRepository, mapperUsuario);
        this.mapperUsuario = mapperUsuario;
        this.usuarioService = new UsuarioService(this.repository);
    }


    @GetMapping("/registro")
    public String registro(Model model, @ModelAttribute("mensaje") String mensajeRecibido){
        UsuarioDto nuevoUsuario = new UsuarioDto();

        model.addAttribute("usuarioDto", nuevoUsuario); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores
        model.addAttribute("mensaje", mensajeRecibido); // Se agrega el mensaje al modelo para poder usarlo en la vista
        return "user/registroUsuario"; // Se retorna el nombre de la vista

    }

    @PostMapping("/registro/guardarUsuario")
    public String guardarUsuario(UsuarioDto usuarioDto, RedirectAttributes redirectAttributes){

        Usuario usuario = mapperUsuario.UsuarioDtoToUsuarioDomain(usuarioDto);
        if(usuarioService.createUsuario(usuario)){
            redirectAttributes.addFlashAttribute("mensaje", "createOk");
            return "redirect:/registro"; // Se redireciona al servicio
        }

        redirectAttributes.addFlashAttribute("mensaje", "createError");
        return "redirect:/registro";

    }

    @GetMapping("/login")
    public String login(Model model, @ModelAttribute("mensaje") String mensajeRecibido){
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute("mensaje", mensajeRecibido);
        return "user/loginUsuario";
    }




}
