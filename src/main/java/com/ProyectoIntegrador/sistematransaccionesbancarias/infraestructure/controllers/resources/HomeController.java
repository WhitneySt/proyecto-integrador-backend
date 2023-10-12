package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.BolsilloServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.BolsilloDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.CuentaDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperBolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
public class HomeController {

    @Autowired
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    CuentaServices cuentaServices;
    CuentaImplementacion repository;
    BolsilloServices bolsilloServices;
    BolsilloImplementacion bolsilloRepository;

    @Autowired
    public HomeController(CuentaJPARepository cuentaJPARepository, BolsilloJPARepository bolsilloJPARepository,MapperCuenta mapperCuenta,MapperBolsillo mapperBolsillo ) {
        this.mapperCuenta = mapperCuenta;
        this.mapperBolsillo = mapperBolsillo;

        this.repository = new CuentaImplementacion(cuentaJPARepository,mapperCuenta);
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository,mapperBolsillo);

        this.cuentaServices = new CuentaServices(this.repository);
        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);
    }

    @GetMapping({"/"})
    public String home(Model model,HttpServletRequest request,@ModelAttribute("mensaje") String mensajeRecibido) {

        Boolean isCuentaCreada = false;
        Boolean isBolsilloCreado = false;

        InformationUsuarioModel(model,request); // Se obtiene el nombre  yla imgdel usuario que inició sesión y lo guarda eel model

        CuentaDto cuentaDto = new CuentaDto();
        BolsilloDto bolsilloDto = new BolsilloDto();
        
        try{
            Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId()); // Se obtiene la cuenta del usuario logeado

            Double totalSaldoBolsillos= bolsilloServices.getTotalSaldoBolsillos(cuenta.getId()); // Se obtiene el saldo disponible del usuario logeado
            Double saldoDisponible= cuenta.getSaldo()-totalSaldoBolsillos; // Se calcula el saldo disponible del usuario logeado

            int ultimosDigitosNumeroCuenta = (int) (cuenta.getId() % 1000);

            model.addAttribute("cuenta", cuenta); // Se agrega la cuenta al modelo para poder usarlo en la vista
            model.addAttribute("saldoDisponible", saldoDisponible); // Se agrega el saldo disponible al modelo para poder usarlo en la vista
            model.addAttribute("ultimosDigitosNumeroCuenta", ultimosDigitosNumeroCuenta); // Se agrega los ultimos digitos del numero de cuenta al modelo para poder usarlo en la vista

            isCuentaCreada = true;
            isBolsilloCreado = true;
        }
        catch(CuentaNotFoundException e){ // Si no se encuentra la cuenta del usuario logeado se puede crear una

            isCuentaCreada = false;
            isBolsilloCreado = false;
            model.addAttribute("cuentaDto", cuentaDto); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores
            model.addAttribute("bolsilloDto", bolsilloDto);
        }
        model.addAttribute("isCuentaCreada", isCuentaCreada); // Se agrega la variable isCuentaCreada al modelo para poder usarlo en la vista
        model.addAttribute("isBolsilloCreado", isBolsilloCreado);

        return "/home";
    }


    @PostMapping("/crearCuenta")
    public String crearCuenta(CuentaDto cuentaDto,RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        cuentaDto.setUsuarioId(usuarioLogeado);
        Cuenta cuenta = mapperCuenta.CuentaDtoToCuentaDomain(cuentaDto);
        boolean guardar = cuentaServices.saveOrUpdateCuenta(cuenta); // Se guarda la cuenta en la base de datos

        if(guardar){
            System.out.println("Se guardo la cuenta");
            redirectAttributes.addFlashAttribute("mensaje","createOk");
        }
        else{
            System.out.println("No se guardo la cuenta");
            redirectAttributes.addFlashAttribute("mensaje","createError");
        }

        return "redirect:/";
    }

    // Obtener el  nombre de usuario que inició sesión y lo pasa al model
    static Model InformationUsuarioModel(Model model,HttpServletRequest request) {
        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión

        String nombreUsuario = usuarioLogeado.getNombre(); // Se obtiene el nombre del usuario que inició sesión y lo guarda en una variable global
        String urlImageUsuario = usuarioLogeado.getUrlImage(); // Se obtiene la url de la imagen del usuario que inició sesión y lo guarda en una variable global

        model.addAttribute("nombreUsuario", nombreUsuario); // Se agrega el nombre del usuario al modelo para poder usarlo en la vista
        model.addAttribute("urlImageUsuario", urlImageUsuario); // Se agrega la url de la imagen del usuario al modelo para poder usarlo en la vista

        return model;
    }




}
