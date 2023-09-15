package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.tags.Param;

@org.springframework.stereotype.Controller
public class Controller {

    private static final String NAMEMENSAJE="mensaje";

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
        model.addAttribute(NAMEMENSAJE, mensajeRecibido); // Se agrega el mensaje al modelo para poder usarlo en la vista
        return "user/registroUsuario"; // Se retorna el nombre de la vista

    }

    @PostMapping("/registro/guardarUsuario")
    public String guardarUsuario(UsuarioDto usuarioDto, RedirectAttributes redirectAttributes){

        Usuario usuario = mapperUsuario.UsuarioDtoToUsuarioDomain(usuarioDto);

        // Se encripta la contraseña
        String passwordEncriptado  = passwordEncoder().encode(usuario.getContrasena());

        // Se cambia la contraseña por la encriptada
        usuario.setContrasena(passwordEncriptado);

        if(usuarioService.createUsuario(usuario)){
            redirectAttributes.addFlashAttribute(NAMEMENSAJE, "createOk");
            return "redirect:/login"; // Se redireciona al servicio
        }
        redirectAttributes.addFlashAttribute(NAMEMENSAJE, "createError");
        return "redirect:/registro";

    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model, @ModelAttribute("mensaje") String mensajeRecibido){// @RequestParam(name = "error", required = false) String error -> permite recibir un parametro por la url y guardarlo en la variable error
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute(NAMEMENSAJE, mensajeRecibido);
        return "user/loginUsuario";
    }

    @GetMapping("/home")
    public String home(Model model){
        return "/home";
    }

    // Encriptar contraseña utilizando el algoritmo de hashing bcrypt
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
