package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class Controller {

    private static final String NAMEMENSAJE="mensaje";

    @Autowired
    private final MapperUsuario mapperUsuario;
    static UsuarioService usuarioService;
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
        return "autenticacion/registroUsuario"; // Se retorna el nombre de la vista

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
        return "autenticacion/loginUsuario";
    }


    // Encriptar contraseña utilizando el algoritmo de hashing bcrypt
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Obtener el usuario que inició sesión
    public static Usuario getUsuarioLogeado(HttpServletRequest request) {

        // request es un objeto que contiene la informacion de la peticion que se hace al servidor
        //System.out.println("Usuario: " + request.getUserPrincipal().getName()); // obtener el nombre del usuario que inició sesion
        Integer idUsuario = Integer.parseInt(request.getUserPrincipal().getName()); // obtener el id del usuario que inició sesion en este el lo toma como el nombre
        Usuario usuarioLogeado = usuarioService.getUsuarioById(idUsuario);  // Se obtiene el usuario que inició sesión

        return usuarioLogeado;
    }






}
