package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.InformationUsuarioModel;

@Controller
public class EstadisticaController {

    MapperCuenta mapperCuenta;
    CuentaServices cuentaServices;
    CuentaImplementacion repository;

    public EstadisticaController(CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta) {
        this.repository = new CuentaImplementacion(cuentaJPARepository,mapperCuenta);
        this.mapperCuenta = mapperCuenta;
        this.cuentaServices = new CuentaServices(this.repository);
    }



    @GetMapping("/estadistica")
    public String estadistica(HttpServletRequest request, Model model){

        Double saldoTotal=0.0;
        Double metaTotal=0.0;
        Double porcentajeCumplido=0.0;

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        InformationUsuarioModel(model,request); // Se obtiene el nombre  yla imgdel usuario que inició sesión y lo guarda eel model

        model.addAttribute("usuarioRol",usuarioLogeado.getRol().getNombre()); // Se guarda el usuario en el model

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


        return "/estadistica";
    }

}
