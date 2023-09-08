package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private MapperClass mapperClass;
    UsuarioService usuarioService;
    UsuarioImplementacion repository;

    @Autowired
    public Controller(UsuarioJPARepository jpaRepository,MapperClass mapperClass) {;
        this.repository = new UsuarioImplementacion(jpaRepository,mapperClass);
        this.mapperClass = mapperClass;
        this.usuarioService = new UsuarioService(this.repository);
    }


    @GetMapping("/registro")
    public String registro(Model model, @ModelAttribute("mensaje") String mensajeRecibido){

        UsuarioDto nuevoUsuario = new UsuarioDto();

        model.addAttribute("usuarioDto", nuevoUsuario); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores

        return "/registroUsuario"; // Se retorna el nombre de la vista

    }

    @PostMapping("/registro/guardarUsuario")
    public String guardarUsuario(UsuarioDto usuarioDto, RedirectAttributes redirectAttributes){

        Usuario usuario = mapperClass.UsuarioDtoToUsuarioDomain(usuarioDto);
        if(usuarioService.saveOrUpdateUsuario(usuario)){
            System.out.println("Usuario guardado correctamente");
            redirectAttributes.addFlashAttribute("mensaje", true);
            return "redirect:/registro"; // Se redireciona al servicio
        }

        redirectAttributes.addFlashAttribute("mensaje", false);
        return "redirect:/registro";

    }




}
