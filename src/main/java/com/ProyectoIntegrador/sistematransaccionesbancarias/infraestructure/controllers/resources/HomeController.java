package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.CuentaDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
public class HomeController {

    @Autowired
    MapperCuenta mapperCuenta;
    static CuentaServices cuentaServices;
    CuentaImplementacion repository;

    @Autowired
    public HomeController(CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta) {
        this.mapperCuenta = mapperCuenta;
        this.repository = new CuentaImplementacion(cuentaJPARepository,mapperCuenta);
        this.cuentaServices = new CuentaServices(this.repository);
    }

    @GetMapping({"/home", "/"})
    public String home(Model model,HttpServletRequest request) {
        CuentaDto cuentaDto = new CuentaDto();

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión

        model.addAttribute("cuentaDto", cuentaDto); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores
        model.addAttribute("nombreUsuario", usuarioLogeado.getNombre()); // Se agrega el nombre del usuario al modelo para poder usarlo en la vista

        return "/home";
    }


    @PostMapping("/crearCuenta")
    public String crearCuenta(CuentaDto cuentaDto, Model model,HttpServletRequest request) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        cuentaDto.setUsuarioId(usuarioLogeado);
        boolean guardar=cuentaServices.saveOrUpdateCuenta(mapperCuenta.CuentaDtoToCuentaDomain(cuentaDto)); // Se guarda la cuenta en la base de datos

        if(guardar){
            System.out.println("Guardando cuenta");
            System.out.println(cuentaDto.toString());
            System.out.println(cuentaDto.toString());

            System.out.println(cuentaDto.toString());

            System.out.println(cuentaDto.toString());
            System.out.println(cuentaDto.toString());


            System.out.println(cuentaDto.toString());


        }
        else{
            System.out.println("No se guardo la cuenta");
            System.out.println("No se guardo la cuenta");

            System.out.println("No se guardo la cuenta");
            System.out.println("No se guardo la cuenta");

            System.out.println("No se guardo la cuenta");

            System.out.println("No se guardo la cuenta");

            System.out.println("No se guardo la cuenta");

            System.out.println("No se guardo la cuenta");



        }

        //model.addAttribute("createOk","mensaje");

        return "redirect:/home";
    }



}
