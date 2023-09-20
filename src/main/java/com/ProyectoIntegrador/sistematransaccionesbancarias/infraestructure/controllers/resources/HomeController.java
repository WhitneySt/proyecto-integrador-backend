package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.BolsilloServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
public class HomeController {

    @Autowired
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    static CuentaServices cuentaServices;
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

    @GetMapping({"/home", "/"})
    public String home(Model model,HttpServletRequest request,@ModelAttribute("mensaje") String mensajeRecibido) {

        Boolean isCuentaCreada = false;

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        model.addAttribute("nombreUsuario", usuarioLogeado.getNombre()); // Se agrega el nombre del usuario al modelo para poder usarlo en la vista

        CuentaDto cuentaDto = new CuentaDto();
        
        try{

            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId()); // Se obtiene la cuenta del usuario logeado

            Double totalSaldoBolsillos= bolsilloServices.getTotalSaldoBolsillos(cuenta.getId()); // Se obtiene el saldo disponible del usuario logeado
            Double saldoDisponible= cuenta.getSaldo()-totalSaldoBolsillos; // Se calcula el saldo disponible del usuario logeado

            int ultimosDigitosNumeroCuenta = (int) (cuenta.getId() % 1000);

            model.addAttribute("cuenta", cuenta); // Se agrega la cuenta al modelo para poder usarlo en la vista
            model.addAttribute("saldoDisponible", saldoDisponible); // Se agrega el saldo disponible al modelo para poder usarlo en la vista
            model.addAttribute("ultimosDigitosNumeroCuenta", ultimosDigitosNumeroCuenta); // Se agrega los ultimos digitos del numero de cuenta al modelo para poder usarlo en la vista

            isCuentaCreada = true;
        }
        catch(CuentaNotFoundException e){ // Si no se encuentra la cuenta del usuario logeado se puede crear una

            isCuentaCreada = false;
            model.addAttribute("cuentaDto", cuentaDto); //se guarda un objeto en el  modelo para poder usarlo en la vista y guardar valores

        }
        model.addAttribute("isCuentaCreada", isCuentaCreada); // Se agrega la variable isCuentaCreada al modelo para poder usarlo en la vista

        return "/home";
    }


    @PostMapping("/crearCuenta")
    public String crearCuenta(CuentaDto cuentaDto,RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        cuentaDto.setUsuarioId(usuarioLogeado);
        boolean guardar=cuentaServices.saveOrUpdateCuenta(mapperCuenta.CuentaDtoToCuentaDomain(cuentaDto)); // Se guarda la cuenta en la base de datos

        if(guardar){
            System.out.println("Se guardo la cuenta");
            redirectAttributes.addFlashAttribute("mensaje","createOk");
        }
        else{
            System.out.println("No se guardo la cuenta");
            redirectAttributes.addFlashAttribute("mensaje","createError");
        }

        return "redirect:/home";
    }



}
