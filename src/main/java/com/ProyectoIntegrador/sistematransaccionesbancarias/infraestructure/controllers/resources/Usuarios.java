package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.EstadoService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.RolService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.UsuarioDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.EstadoJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.RolJPAEntity;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("usuario")UsuarioJPAEntity usuario, RedirectAttributes redirectAttributes) {
        System.out.println("Actualizando usuario");
        System.out.println(usuario.toString());
        try {
            usuarioServices.UpdateUsuario(mapperUsuario.UsuarioJPAToUsuarioDomain(usuario));
            System.out.println("Usuario actualizado correctamente");
            redirectAttributes.addFlashAttribute("mensaje", "updateOk");
        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario");
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "updateError");
        }

        return "redirect:/usuarios";
    }


}
