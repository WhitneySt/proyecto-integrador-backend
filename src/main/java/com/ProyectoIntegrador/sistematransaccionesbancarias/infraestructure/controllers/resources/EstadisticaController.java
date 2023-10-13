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


    @GetMapping("/estadistica")
    public String estadistica(HttpServletRequest request, Model model){

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

                // saca el porcentaje de la meta de ahorro completada
                porcentajeCumplido = (saldoTotal / metaTotal) * 100;

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


        }
        model.addAttribute("usuarioRol",usuarioLogeado.getRol().getNombre()); // Se guarda el usuario en el model
        
        return "/estadistica";
    }

}
