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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    /* ?
    * Se crearon dos métodos para manejar la visualización de la información de un usuario.
    * El primer método recibe un ID como parámetro y se encarga de obtener los detalles del usuario.
    * Luego, redirige al servicio de visualización de usuario, pasando el usuario obtenido como un atributo de modelo utilizando  @ModelAttribute .
    * Se optó por este enfoque debido a que, al retornar directamente la plantilla desde el método  verDetalle  con el parámetro ID, los estilos y las imágenes no se mostraban correctamente debido a las restricciones de Spring Security.
  */
    @GetMapping("/detalleUsuario/{id}")
    // @pathVariable sirve para obtener el valor de la variable que se encuentra en la url
    public String verDetalle(Model model, @PathVariable int id,RedirectAttributes redirectAttributes){

        try {
            Usuario usuario = usuarioServices.getUsuarioById(id);
            redirectAttributes.addFlashAttribute("usuario", usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/verUsuario";

    }

    // Por otro lado, el segundo método no requiere parámetros y se encarga de mostrar los estilos y las imágenes correctamente.
    // Al no tener restricciones adicionales, la plantilla se renderiza adecuadamente con todos los elementos visuales.

    @GetMapping("/verUsuario")
    public String verUsuario(Model model,@ModelAttribute("usuario") Usuario usuario){

        model.addAttribute("usuario",usuario);

        return "users/informationUser";
    }

    @GetMapping("/getInformationUser/{id}")
    public String editarUsuario(@PathVariable int id,RedirectAttributes redirectAttributes){

        try {
            Usuario usuario = usuarioServices.getUsuarioById(id);
            redirectAttributes.addFlashAttribute("usuario", usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/editarUsuario";

    }

    @GetMapping("/editarUsuario")
    public String editarUsuario(Model model,@ModelAttribute("usuario") Usuario usuario){

        model.addAttribute("usuario",usuario);

        return "users/editUser";
    }


}
