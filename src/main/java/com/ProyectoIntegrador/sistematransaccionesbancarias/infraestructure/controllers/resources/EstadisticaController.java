package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario.UsuarioJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.InformationUsuarioModel;

@Controller
public class EstadisticaController {

    MapperCuenta mapperCuenta;
    CuentaServices cuentaServices;
    CuentaImplementacion repository;
    MapperUsuario mapperUsuario;
    UsuarioService usuarioServices;
    UsuarioImplementacion repositoryUsuario;

    public EstadisticaController(CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta, UsuarioJPARepository usuarioJPARepository, MapperUsuario mapperUsuario) {
        this.repository = new CuentaImplementacion(cuentaJPARepository,mapperCuenta);
        this.mapperCuenta = mapperCuenta;
        this.cuentaServices = new CuentaServices(this.repository);

        this.repositoryUsuario = new UsuarioImplementacion(usuarioJPARepository,mapperUsuario);
        this.mapperUsuario = mapperUsuario;
        this.usuarioServices = new UsuarioService(this.repositoryUsuario);
    }



    @GetMapping("/estadistica")
    public String estadistica(HttpServletRequest request, Model model){

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        InformationUsuarioModel(model,request); // Se obtiene el nombre  yla imgdel usuario que inició sesión y lo guarda eel model

        model.addAttribute("usuarioRol",usuarioLogeado.getRol().getNombre()); // Se guarda el usuario en el model

        String rolUsuario=usuarioLogeado.getRol().getNombre();

        if(rolUsuario.equals("Usuario")){

            Double saldoTotal=0.0;
            Double metaTotal=0.0;
            Double porcentajeCumplido=0.0;

            // Obtiene los datos de la estadistica del usuario logeado
            try{

                Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId()); // Se obtiene la cuenta del usuario logeado

                saldoTotal= cuenta.getSaldo();
                metaTotal = cuenta.getMetaAhorro();

                // saca el porcentaje de la meta de ahorro completada
                porcentajeCumplido = (saldoTotal / metaTotal) * 100;

            }

            catch(CuentaNotFoundException e){ // Si no se encuentra la cuenta del usuario logeado se puede crear una

                System.out.println("No se encontró la cuenta del usuario logeado");

            }

            model.addAttribute("saldoTotal", saldoTotal.intValue());
            model.addAttribute("metaTotal", metaTotal.intValue());
            model.addAttribute("porcentajeMeta",porcentajeCumplido.intValue());

        }
        else if(rolUsuario.equals("Administrador")){

            List <Usuario> usuarios = usuarioServices.getAllUsuarios(); // Se obtienen todos los usuarios

            Integer cantidadUsuarios = usuarios.size(); // Se obtiene la cantidad de usuarios
            // Cantidad de usuarios activos true
            Integer cantidadUsuariosActivos = usuarios.stream().filter(usuario -> usuario.getEstado().getNombre().equals(true)).toArray().length;

            // recorre la lista de usuarios
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getEstado().getNombre());
            }
            System.out.println("Cantidad de usuarios: "+cantidadUsuarios);
            System.out.println("Cantidad de usuarios activos: "+cantidadUsuariosActivos);


        }




        return "/estadistica";
    }

}
