package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.BolsilloServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperBolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.BolsilloController.InformationUsuarioModel;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;

@Controller
@Tag(name = "Estadísticas", description = "Endpoints para obtener estadísticas de usuarios, cuentas y movimientos dependiendo del rol del usuario que ha iniciado sesión")
public class EstadisticaController {

    MapperCuenta mapperCuenta;
    CuentaServices cuentaServices;
    CuentaImplementacion repository;

    MapperUsuario mapperUsuario;
    UsuarioService usuarioServices;
    UsuarioImplementacion repositoryUsuario;

    MapperBolsillo mapperBolsillo;
    BolsilloServices bolsilloServices;
    BolsilloImplementacion bolsilloRepository;

    public EstadisticaController(CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta, UsuarioJPARepository usuarioJPARepository, MapperUsuario mapperUsuario, BolsilloJPARepository bolsilloJPARepository, MapperBolsillo mapperBolsillo) {

        this.mapperCuenta = mapperCuenta;
        this.repository = new CuentaImplementacion(cuentaJPARepository,mapperCuenta);
        this.cuentaServices = new CuentaServices(this.repository);

        this.mapperUsuario = mapperUsuario;
        this.repositoryUsuario = new UsuarioImplementacion(usuarioJPARepository,mapperUsuario);
        this.usuarioServices = new UsuarioService(this.repositoryUsuario);

        this.mapperBolsillo = mapperBolsillo;
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository,mapperBolsillo);
        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);

    }

    @Operation(summary = "Obtener estadísticas", description = "Obtiene las estadísticas de los usuarios y cuentas según el rol del usuario que ha iniciado sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Usuario", value = "<html><body><h1>Estadistica Usuario</h1></body></html>"),
                            @ExampleObject(name = "Administrador", value = "<html><body><h1>Estadistica Administrador</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "401", description = "No se ha iniciado sesión", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Iniciar sesión</h1></body></html>"))),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Acceso denegado</h1></body></html>"))),
    })
    @GetMapping("/estadistica")
    public String estadistica(@Parameter(description = "Se  obtiene la información del usuario logeado para determinar su rol y de esta manera mostrar su estadistica", example = "5") HttpServletRequest request, Model model){

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        InformationUsuarioModel(model,request); // Se obtiene el nombre  yla imgdel usuario que inició sesión y lo guarda eel model

        String rolUsuario=usuarioLogeado.getRol().getNombre();

        if(rolUsuario.equals("Usuario")){

            Double saldoTotal=0.0;
            Double metaTotal=0.0;
            double porcentajeCumplido=0.0;

            // Obtiene los datos de la estadistica del usuario logeado
            try{

                Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId()); // Se obtiene la cuenta del usuario logeado

                saldoTotal= cuenta.getSaldo();

                metaTotal = cuenta.getMetaAhorro();

                if (( metaTotal != null && metaTotal != 0 ) &&  (saldoTotal != null && saldoTotal != 0)) {
                    //Se  saca el porcentaje de la meta de ahorro completada si es que tiene una meta de ahorro y saldo
                    porcentajeCumplido = (saldoTotal / metaTotal) * 100;
                } else {
                    porcentajeCumplido = 0.0;
                    metaTotal = 0.0;
                }

            }

            catch(CuentaNotFoundException e){
                // Los valores por defecto son 0.0
                System.out.println("No se encontró la cuenta del usuario logeado");

            }

            model.addAttribute("saldoTotal", saldoTotal.intValue());
            model.addAttribute("metaTotal", metaTotal.intValue());
            model.addAttribute("porcentajeMeta", (int) porcentajeCumplido);

        }
        else if(rolUsuario.equals("Administrador")){

            List <Usuario> usuarios = usuarioServices.getAllUsuarios();
            List <Bolsillo> bolsillos = bolsilloServices.getAllBolsillos();

            // ? TODO : Preguntar que forma es mejor para obtener los datos, si de la base de datos por medio de los servicios o desde el controlador haciendo algunas operaciones
            Integer cantidadCuentasCreadas = cuentaServices.getCantidadCuentas();
            Integer cantidadCuentasActivas = cuentaServices.getCantidadCuentasActivas();

            Long totalDineroCuentas = cuentaServices.getTotalDineroCuentas();
            Long promedioDineroCuentas = cuentaServices.getPromedioDineroCuentas();
            Integer cantidadCuentasConMetas = cuentaServices.getCantidadCuentasConMetas();
            Integer cantidadCuentasCumplenMetaAhorro = cuentaServices.getCantidadCuentasCumplenMetaAhorro();


            // Obtiene los datos de la estadistica de los usuarios
            int cantidadUsuarios = usuarios.size();
            int cantidadUsuariosActivos = usuarios.stream().filter(usuario -> usuario.getEstado().getNombre().equals(true)).toArray().length;
            int cantidadUsuariosInactivos =  usuarios.stream().filter(usuario -> usuario.getEstado().getNombre().equals(false)).toArray().length;
            Integer cantidadUsuariosTipoUsuario = usuarios.stream().filter(usuario -> usuario.getRol().getNombre().equals("Usuario")).toArray().length;
            Integer cantidadUsuariosTipoAdministrador = usuarios.stream().filter(usuario -> usuario.getRol().getNombre().equals("Administrador")).toArray().length;

            // Obtiene los datos de la estadistica de los bolsillos
            int cantidadBolsillos = bolsillos.size();

            model.addAttribute("cantidadUsuarios", cantidadUsuarios);
            model.addAttribute("cantidadUsuariosActivos", cantidadUsuariosActivos);
            model.addAttribute("cantidadUsuariosInactivos", cantidadUsuariosInactivos);
            model.addAttribute("cantidadUsuariosTipoUsuario", cantidadUsuariosTipoUsuario);
            model.addAttribute("cantidadUsuariosTipoAdministrador", cantidadUsuariosTipoAdministrador);

            model.addAttribute("cantidadBolsillos", cantidadBolsillos);

            model.addAttribute("cantidadCuentasCreadas",cantidadCuentasCreadas );
            model.addAttribute("cantidadCuentasActivas",cantidadCuentasActivas );

            model.addAttribute("totalDineroCuentas",totalDineroCuentas );
            model.addAttribute("promedioDineroCuentas",promedioDineroCuentas );
            model.addAttribute("cantidadCuentasConMetas",cantidadCuentasConMetas );
            model.addAttribute("cantidadCuentasCumplenMetaAhorro",cantidadCuentasCumplenMetaAhorro );


        }
        model.addAttribute("usuarioRol",usuarioLogeado.getRol().getNombre()); // Se guarda el usuario en el model
        
        return "/estadistica";
    }

}
