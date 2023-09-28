package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.EstadoService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.RolService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.Rol.RolImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.estado.EstadoImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.estado.EstadoJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.Rol.RolJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperEstado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperRol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.passwordEncoder;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.NombreUsuarioModel;

@Controller
public class Usuarios {

    @Autowired
    MapperUsuario mapperUsuario;
    MapperEstado mapperEstado;
    MapperRol mapperRol;
    UsuarioService usuarioServices;
    EstadoService estadoService;
    RolService rolService;
    UsuarioImplementacion repository;
    EstadoImplementacion repositoryEstado;
    RolImplementacion repositoryRol;

    @Autowired
    public Usuarios(UsuarioJPARepository usuarioJPARepository,MapperUsuario mapperUsuario,EstadoJPARepository estadoJPARepository, MapperEstado mapperEstado,RolJPARepository rolJPARepository,MapperRol mapperRol) {
        this.repository = new UsuarioImplementacion(usuarioJPARepository,mapperUsuario);
        this.repositoryEstado = new EstadoImplementacion(estadoJPARepository,mapperEstado);
        this.repositoryRol = new RolImplementacion(rolJPARepository,mapperRol);
        this.usuarioServices = new UsuarioService(this.repository);
        this.estadoService = new EstadoService(this.repositoryEstado);
        this.rolService = new RolService(this.repositoryRol);
        this.mapperUsuario = mapperUsuario;
        this.mapperEstado = mapperEstado;
        this.mapperRol = mapperRol;

    }

    // ? Adminitración de usuarios (CRUD)
    @GetMapping("/usuarios")
    public String usuarios(Model model,HttpServletRequest request) {

        NombreUsuarioModel(model,request); // Se obtiene el nombre del usuario que inició sesión y lo guarda en el model

        List<Usuario> listaUsuarios = usuarioServices.getAllUsuarios();
        model.addAttribute("usuarios",listaUsuarios);

        return "users/users";
    }

    /* ?
     * Se crearon dos métodos para manejar la visualización de la información de un usuario.
     * El primer método recibe un ID como parámetro y un activo  y se encarga de obtener los detalles del usuario.
     * Luego, redirige al servicio de visualización de usuario dependendiendo del valor del action, pasando el usuario obtenido como un atributo de modelo utilizando  @ModelAttribute .
     * Se optó por este enfoque debido a que, al retornar directamente la plantilla desde el método  verDetalle  con el parámetro ID, los estilos y las imágenes no se mostraban correctamente debido a las restricciones de Spring Security.
     */

    @GetMapping("/getInformationUser/{action}/{id}")
    public String getInformationUser(@PathVariable String action, @PathVariable int id, RedirectAttributes redirectAttributes) {
        // intenta obtener el usuario con el id especificado
        try {
            UsuarioJPAEntity usuario = mapperUsuario.UsuarioDomainToUsuarioJPA(usuarioServices.getUsuarioById(id));
            redirectAttributes.addFlashAttribute("usuario", usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Redirige a la página correspondiente según la acción especificada
        if (action.equals("verUsuario")) {
            return "redirect:/verUsuario";
        } else if (action.equals("editarUsuario")) {
            return "redirect:/editarUsuario";
        }

        // En caso de que la acción no coincida con ninguna opción, puedes redirigir a una página de error o realizar alguna otra acción
        return "redirect:/error";
    }

    // Por otro lado, estos métodos no requiere parámetros y solo recibe la información de un usuario que llega desde getInformationUser al ser redireccionado se encarga de mostrar los estilos y las imágenes correctamente.
    // Al no tener restricciones adicionales, la plantilla se renderiza adecuadamente con todos los elementos visuales.

    @GetMapping("/verUsuario")
    public String verUsuario(Model model,@ModelAttribute("usuario") UsuarioJPAEntity usuario){

        model.addAttribute("usuario",usuario);

        return "users/informationUser";
    }


    @GetMapping("/editarUsuario")
    public String editarUsuario(Model model,@ModelAttribute("usuario") UsuarioJPAEntity usuario){

        List<Estado> estados = estadoService.getAllEstados();
        List<Rol> roles = rolService.getAllRoles();
        roles.forEach(rol -> System.out.println(rol.toString()));

        model.addAttribute("listaEstados",estados);
        model.addAttribute("listaRoles",roles);
        System.out.println(usuario.toString());
        model.addAttribute("usuario",usuario);

        return "users/editUser";
    }

    //? Se trabaja con el objeto UsuarioJPAEntity para poder hacer la realcion con uroles y estados porque si lo uso con un objeto dto genera error
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("usuario")UsuarioJPAEntity usuario, RedirectAttributes redirectAttributes) {

        try {
            usuarioServices.UpdateUsuario(mapperUsuario.UsuarioJPAToUsuarioDomain(usuario));
            redirectAttributes.addFlashAttribute("mensaje", "updateOk");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "updateError");
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/crearUsuario")
    public String crearUsuario(Model model) {

        List<Estado> estados = estadoService.getAllEstados();
        List<Rol> roles = rolService.getAllRoles();

        model.addAttribute("listaEstados",estados);
        model.addAttribute("listaRoles",roles);
        model.addAttribute("usuario",new Usuario());

        return "users/createUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("usuario")UsuarioJPAEntity usuario, RedirectAttributes redirectAttributes) {

        // Se encripta la contraseña
        String passwordEncriptado  = passwordEncoder().encode(usuario.getContrasena());

        // Se cambia la contraseña por la encriptada
        usuario.setContrasena(passwordEncriptado);

        if(usuarioServices.createUsuario(mapperUsuario.UsuarioJPAToUsuarioDomain(usuario))){
            redirectAttributes.addFlashAttribute("mensaje", "createOk");
            return "redirect:/usuarios"; // Se redireciona al servicio
        }

        redirectAttributes.addFlashAttribute("mensaje", "createError");
        return "redirect:/usuarios";
    }

    // ? Administración del pefil de usuario logeado

    @GetMapping("/perfil")
    public String perfil(Model model,HttpServletRequest request) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        model.addAttribute("usuario",usuarioLogeado);

        return "profile/seeProfile";
    }

    @GetMapping("/editarPerfil")
    public String editarPerfil(Model model, HttpServletRequest request ) {
        
        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión

        model.addAttribute("usuario",usuarioLogeado);

        return "profile/editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("usuario")UsuarioJPAEntity usuario, RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile imagen) {

        // @RequestParam("file") MultipartFile file -> Se obtiene la imagen del formulario y se guarda en un objeto MultipartFile
        boolean updateProfile = true;

        // Validaciones de la imagen, si la imagen no está vacia se guarda obtiene la url de la imagen y se guarda en el usuario
        if(!imagen.isEmpty()){

            System.out.println("Se intenta guardar la imagen");

            Path directorioImagenes = Paths.get("src//main//resources//static//images/profile"); // Se obtiene la ruta de la carpeta donde se guardará la imagen
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath(); // Se obtiene la ruta absoluta de la carpeta

        // Se intenta guardar la imagen en la carpeta
        try {

            byte[] bytesImg = imagen.getBytes(); // Se obtienen los bytes de la imagen
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename()); // Se obtiene la ruta completa de la imagen
            Files.write(rutaCompleta, bytesImg); // Se guarda la imagen en la ruta especificada
            usuario.setUrlImage(imagen.getOriginalFilename()); // Se guarda la url de la imagen en el usuario que es el nombre de la imagen

            updateProfile = true; // Se puede actualizar el perfil porque se pudo guardar la imagen

            } catch (Exception e) {
                updateProfile = false; // No se guarda el perfil porque no se pudo guardar la imagen
                System.out.println(e.getMessage());
                System.out.println("No se pudo guardar la imagen");
            }
        }

        if(updateProfile){
            // se intenta actualizar el usuario
            try {
                System.out.println("Se intenta actualizar el usuario");
                usuarioServices.UpdateUsuario(mapperUsuario.UsuarioJPAToUsuarioDomain(usuario));
                redirectAttributes.addFlashAttribute("mensaje", "updateOk");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                redirectAttributes.addFlashAttribute("mensaje", "updateError");
            }
        }else{
            redirectAttributes.addFlashAttribute("mensaje", "updateErrorImg");
        }

        return "redirect:/perfil";
    }




}
