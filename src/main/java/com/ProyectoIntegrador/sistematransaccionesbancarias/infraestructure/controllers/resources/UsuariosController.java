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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Map;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.passwordEncoder;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.InformationUsuarioModel;

@Controller
@Tag(name = "Usuarios", description = "Endpoints relacionados con la administración de usuarios")
public class UsuariosController {

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
    public UsuariosController(UsuarioJPARepository usuarioJPARepository, MapperUsuario mapperUsuario, EstadoJPARepository estadoJPARepository, MapperEstado mapperEstado, RolJPARepository rolJPARepository, MapperRol mapperRol) {
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

    @Operation(summary = "Obtiene la lista de usuarios", description = "Este endpoint obtiene la lista de usuarios y la muestra en la vista.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Lista de usuarios...</body></html>")))
    @GetMapping("/usuarios")
    public String usuarios(Model model,HttpServletRequest request) {

        InformationUsuarioModel(model,request); // Se obtiene el nombre del usuario y la img que inició sesión y lo guarda en el model

        List<Usuario> listaUsuarios = usuarioServices.getAllUsuarios();
        model.addAttribute("usuarios",listaUsuarios);

        System.out.println("Lista de images");
        getImagesCloudinary();



        return "users/users";
    }

    /* ?
     * Se crearon dos métodos para manejar la visualización de la información de un usuario.
     * El primer método recibe un ID como parámetro y un activo  y se encarga de obtener los detalles del usuario.
     * Luego, redirige al servicio de visualización de usuario dependendiendo del valor del action, pasando el usuario obtenido como un atributo de modelo utilizando  @ModelAttribute .
     * Se optó por este enfoque debido a que, al retornar directamente la plantilla desde el método  verDetalle  con el parámetro ID, los estilos y las imágenes no se mostraban correctamente debido a las restricciones de Spring Security.
     */

    @GetMapping("/getInformationUser/{action}/{id}")
    @Operation(summary = "Redirecciona para ver o editar un usuario", description = "Redirecciona para ver o editar un usuario según la acción especificada.")
    @ApiResponse(responseCode = "200", description = "Redirección exitosa a la acción especificada", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Redirección exitosa...</body></html>")))
    @ApiResponse(responseCode = "400", description = "Error en la redirección", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Error en la redirección...</body></html>")))
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
    @Operation(summary = "Muestra la información de un usuario", description = "Muestra la información detallada de un usuario.")
    @ApiResponse(responseCode = "200", description = "Información de usuario mostrada exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Información de usuario...</body></html>")))
    public String verUsuario(Model model,@ModelAttribute("usuario") UsuarioJPAEntity usuario){

        model.addAttribute("usuario",usuario);

        return "users/informationUser";
    }


    @GetMapping("/editarUsuario")
    @Operation(summary = "Muestra el formulario de edición de un usuario", description = "Muestra el formulario de edición de un usuario.")
    @ApiResponse(responseCode = "200", description = "Formulario de edición de usuario mostrado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Formulario de edición de usuario...</body></html>")))
    public String editarUsuario(Model model,@ModelAttribute("usuario") UsuarioJPAEntity usuario){

        List<Estado> estados = estadoService.getAllEstados();
        List<Rol> roles = rolService.getAllRoles();

        model.addAttribute("listaEstados",estados);
        model.addAttribute("listaRoles",roles);
        model.addAttribute("usuario",usuario);

        return "users/editUser";
    }

    //? Se trabaja con el objeto UsuarioJPAEntity para poder hacer la realcion con uroles y estados porque si lo uso con un objeto dto genera error
    @PostMapping("/updateUser")
    @Operation(summary = "Actualiza un usuario", description = "Actualiza un usuario con la información proporcionada.")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Usuario actualizado...</body></html>")))
    @ApiResponse(responseCode = "400", description = "Error al actualizar el usuario", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Error al actualizar el usuario...</body></html>")))
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
    @Operation(summary = "Muestra el formulario de creación de usuario", description = "Muestra el formulario de creación de un nuevo usuario.")
    @ApiResponse(responseCode = "200", description = "Formulario de creación de usuario mostrado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Formulario de creación de usuario...</body></html>")))
    public String crearUsuario(Model model) {

        List<Estado> estados = estadoService.getAllEstados();
        List<Rol> roles = rolService.getAllRoles();

        model.addAttribute("listaEstados",estados);
        model.addAttribute("listaRoles",roles);
        model.addAttribute("usuario",new Usuario());

        return "users/createUser";
    }

    @PostMapping("/saveUser")
    @Operation(summary = "Crea un nuevo usuario", description = "Crea un nuevo usuario con la información proporcionada.")
    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Usuario creado exitosamente...</body></html>")))
    @ApiResponse(responseCode = "400", description = "Error al crear el usuario", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Error al crear el usuario...</body></html>")))
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
    @Operation(summary = "Muestra el perfil del usuario logeado", description = "Muestra el perfil del usuario que ha iniciado sesión.")
    @ApiResponse(responseCode = "200", description = "Perfil de usuario mostrado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Perfil de usuario...</body></html>")))
    public String perfil(Model model,HttpServletRequest request) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        String urlImageUsuario = usuarioLogeado.getUrlImage(); // Se obtiene la url de la imagen del usuario que inició sesión y lo guarda en una variable global

        model.addAttribute("urlImageUsuario", urlImageUsuario); // Se agrega la url de la imagen del usuario al modelo para poder usarlo en la vista
        model.addAttribute("usuario",usuarioLogeado);

        return "profile/seeProfile";
    }

    @GetMapping("/editarPerfil")
    @Operation(summary = "Muestra el formulario de edición del perfil del usuario logeado", description = "Muestra el formulario de edición del perfil del usuario que ha iniciado sesión.")
    @ApiResponse(responseCode = "200", description = "Formulario de edición de perfil de usuario mostrado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Formulario de edición de perfil de usuario...</body></html>")))
    public String editarPerfil(Model model, HttpServletRequest request ) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        String urlImageUsuario = usuarioLogeado.getUrlImage(); // Se obtiene la url de la imagen del usuario que inició sesión y lo guarda en una variable global

        model.addAttribute("urlImageUsuario", urlImageUsuario); // Se agrega la url de la imagen del usuario al modelo para poder usarlo en la vista
        model.addAttribute("usuario",usuarioLogeado);

        return "profile/editProfile";
    }

    @PostMapping("/updateProfile")
    @Operation(summary = "Actualiza el perfil del usuario logeado", description = "Actualiza el perfil del usuario que ha iniciado sesión con la información proporcionada.")
    @ApiResponse(responseCode = "200", description = "Perfil de usuario actualizado exitosamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Perfil de usuario actualizado...</body></html>")))
            @ApiResponse(responseCode = "400", description = "Error al actualizar el perfil del usuario", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body>Error al actualizar el perfil del usuario...</body></html>")))
    public String updateProfile(@ModelAttribute("usuario")Usuario usuario, RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile imagen) {

        // @RequestParam("file") MultipartFile file -> Se obtiene la imagen del formulario y se guarda en un objeto MultipartFile
        boolean updateProfile = true;

        // Validaciones de la imagen, si la imagen no está vacia indica que se seleccionó una nueva iamgen que se debe guardar
        if(!imagen.isEmpty()){

            // Se guarda la imagen en Cloudinary

            deleteImageCloudinary(usuario.getUrlImage()); // se elimina la imagen anterior de cloudinary para no tener imagenes repetidas
            updateProfile=updateCloudinary(imagen,usuario); //guarda la imagen en cloudinary y si se guarda correctamente retorna true y  si no reotrna flase

        }
        // si la imagen está vacia se guarda la url de la imagen que ya tenía el usuario
        else{

            Usuario usuarioLogeado = usuarioServices.getUsuarioById(usuario.getId());
            usuario.setUrlImage(usuarioLogeado.getUrlImage());

        }

        if(updateProfile){
            // se intenta actualizar el usuario
            try {
                usuarioServices.UpdateUsuario(usuario);
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

    // Este método se encarga de guardar la imagen en la carpeta images/profile
    public boolean saveImgLocal(MultipartFile imagen){

        Path directorioImagenes = Paths.get("src//main//resources//static//images/profile"); // Se obtiene la ruta de la carpeta donde se guardará la imagen
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath(); // Se obtiene la ruta absoluta de la carpeta

        // Se intenta guardar la imagen en la carpeta
        try {

            byte[] bytesImg = imagen.getBytes(); // Se obtienen los bytes de la imagen
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename()); // Se obtiene la ruta completa de la imagen
            Files.write(rutaCompleta, bytesImg); // Se guarda la imagen en la ruta especificada

            return true; // Se puede actualizar el perfil porque se pudo guardar la imagen

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("No se pudo guardar la imagen");
            return false; // No se puede actualizar el perfil porque no se pudo guardar la imagen
        }

    }

    // Este metodo se encarga de subir la imagen a  cloudinary y guardar la url de la imagen en la base de datos
    public  boolean updateCloudinary(MultipartFile imagen, Usuario usuario){

        // datos de la cuenta de cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dkzspm2fj",
                "api_key", "229982374928582",
                "api_secret", "ZM54qomggmRWESmK2QQgui7_WPo"));

        try {
            Map<?, ?> uploadResult= cloudinary.uploader().upload(imagen.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            usuario.setUrlImage(imageUrl);
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo guardar la imagen");
            System.out.println(e.getMessage());
            return false;
        }

    }


    // Este metodo obtiene la lista de todas las imágenes subidas a tu cuenta de Cloudinary
public void getImagesCloudinary(){

        // datos de la cuenta de cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dkzspm2fj",
                "api_key", "229982374928582",
                "api_secret", "ZM54qomggmRWESmK2QQgui7_WPo"));

        try {
            Map<?, ?> result = cloudinary.api().resources(ObjectUtils.emptyMap()); // Obtiene la lista de imágenes
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("No se pudo obtener la lista de imágenes");
            System.out.println(e.getMessage());
        }

    }

    // Este método se encarga de eliminar una imagen de Cloudinary
    public void deleteImageCloudinary(String public_id){

        // datos de la cuenta de cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dkzspm2fj",
                "api_key", "229982374928582",
                "api_secret", "ZM54qomggmRWESmK2QQgui7_WPo"));

        try {
            Map<?, ?> result = cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap()); // Elimina la imagen
            System.out.println(result); // Imprime el resultado de la eliminación
            System.out.println("Imagen eliminada"+public_id);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar la imagen");
            System.out.println(e.getMessage());
        }

    }

    // Este método se encarga de eliminar todas las fotos
    public void deleteAllImagesCloudinary(){

        // datos de la cuenta de cloudinary
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dkzspm2fj",
                "api_key", "229982374928582",
                "api_secret", "ZM54qomggmRWESmK2QQgui7_WPo"));

        try {
            Map<?, ?> result = cloudinary.api().deleteAllResources(ObjectUtils.emptyMap()); // Elimina todas las imágenes
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("No se pudo eliminar la imagen");
            System.out.println(e.getMessage());
        }

    }




}
