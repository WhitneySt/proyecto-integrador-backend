package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;

import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security.Auth.AuthResponse;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.security.Auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
@Tag(name = "Autenticación", description = "Endpoints relacionados con la autenticación de usuarios")
public class Controller {

    private static final String NAMEMENSAJE="mensaje";

    // ? Inyección de dependencias
    @Autowired
    private final MapperUsuario mapperUsuario;
    static UsuarioService usuarioService;
    UsuarioImplementacion repository;
    private final AuthService authService;

    @Autowired
    public Controller(UsuarioJPARepository jpaRepository, MapperUsuario mapperUsuario, AuthService authService) {
        this.authService = authService;
        this.repository = new UsuarioImplementacion(jpaRepository, mapperUsuario);
        this.mapperUsuario = mapperUsuario;
        this.usuarioService = new UsuarioService(this.repository);

    }

    @Operation(summary = "Muestra el formulario de registro", description = "Este endpoint muestra el formulario de registro para que los usuarios puedan crear una cuenta en el sistema.o")
    @ApiResponse(responseCode = "200", description = "Vista HTML del formulario de registro con campos para ingresar los datos del usuario como nombre, número de identificación, correo electrónico y  contraseña" , content =
    @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><form>...</form></body></html>")))
    @GetMapping("/registro")
    public String registro(Model model,  @Parameter(description = "Mensaje de error en caso de fallar el registro") @ModelAttribute("mensaje") String mensajeRecibido){
        UsuarioDto nuevoUsuario = new UsuarioDto();

        model.addAttribute("usuarioDto", nuevoUsuario); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores
        model.addAttribute(NAMEMENSAJE, mensajeRecibido); // Se agrega el mensaje al modelo para poder usarlo en la vista
        return "autenticacion/registroUsuario"; // Se retorna el nombre de la vista

    }

    @Operation(summary = "Guarda un nuevo usuario", description = "Este endpoint guarda un nuevo usuario en el sistema. Realiza validaciones y devuelve un mensaje de éxito o error junto con un token de autenticación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario guardado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redireccionando al home...</body></html>"))),
            @ApiResponse(responseCode = "401", description = "Datos de registro incorrectos", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redireccionando al formulario de registro...</body></html>"))),
            @ApiResponse(responseCode = "400", description = "Error al guardar el usuario", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redireccionando al formulario de registro...</body></html>")))
    })
    @PostMapping("/registro/guardarUsuario")
    public String guardarUsuario(@Parameter(description = "Datos del usuario a guardar") UsuarioDto usuarioDto, RedirectAttributes redirectAttributes){

        Usuario usuario = mapperUsuario.UsuarioDtoToUsuarioDomain(usuarioDto);
        // validaciones
        AuthResponse token=authService.register(usuario);
        if (token.getToken() != null){
            System.out.println("Registro exitoso");
            System.out.println("Token: " + token.getToken());
            //return ResponseEntity.ok(token); esto es para postMan
            redirectAttributes.addFlashAttribute(NAMEMENSAJE, "createOk");
            return "redirect:/login"; // Se redireciona al servicio
        }
        else{
            System.out.println("Error al registrar");
            System.out.println("Token: " + token.getToken());
            // return ResponseEntity.badRequest().body(token); esto es para postMan
            redirectAttributes.addFlashAttribute(NAMEMENSAJE, "createError");
            return "redirect:/registro";
        }

    }

    @Operation(summary = "Muestra el formulario de inicio de sesión", description = "Este endpoint muestra el formulario de inicio de sesión para que los usuarios puedan acceder a su cuenta en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redireccionando al home...</body></html>"))),
            @ApiResponse(responseCode = "400", description = "Inicio de sesión fallido", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redireccionando al login...</body></html>"))),
            @ApiResponse(responseCode = "401", description = "Datos de inicio de sesión incorrectos", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redireccionando al formulario de registro...</body></html>")))
    })
    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model,  @Parameter(description = "Mensaje de error en caso de fallar el inicio de sesión") @ModelAttribute("mensaje") String mensajeRecibido){// @RequestParam(name = "error", required = false) String error -> permite recibir un parametro por la url y guardarlo en la variable error
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute(NAMEMENSAJE, mensajeRecibido);
        return "autenticacion/loginUsuario";
    }

    //Controlador que  lleva al template de No autorizado
    @Operation(summary = "Muestra la página de acceso denegado", description = " Este endpoint muestra la página de acceso denegado cuando un usuario intenta acceder a una página protegida sin los permisos adecuados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Acceso denegado. El usuario no tiene los permisos necesarios para acceder a esta página por  lo que se muestra la página de acceso denegado",
                    content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Acceso denegado</h1></body></html>")))
    })
    // @RequestMapping(value="/accessdenied")
    @GetMapping("/accessdenied")
    public String accesoDenegado(){
        System.out.println("Acceso denegado");
        return "accessDenied";
    }


    // Encriptar contraseña utilizando el algoritmo de hashing bcrypt
    public static PasswordEncoder passwordEncoder() {
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
