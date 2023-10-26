package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.BolsilloServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.TransaccionServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Cuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.BolsilloDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.CuentaDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion.TransaccionImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion.TransaccionJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperBolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTransaccion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
@Tag(name = "Home", description = "Endpoints para la página de inicio y creación de cuentas")
public class HomeController {

    @Autowired
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    MapperTransaccion mapperTransaccion;

    CuentaServices cuentaServices;
    BolsilloServices bolsilloServices;
    TransaccionServices transaccionServices;
    CuentaImplementacion repository;
    BolsilloImplementacion bolsilloRepository;
    TransaccionImplementacion transaccionRepository;

    @Autowired
    public HomeController(CuentaJPARepository cuentaJPARepository, BolsilloJPARepository bolsilloJPARepository, TransaccionJPARepository transaccionJPARepository, MapperCuenta mapperCuenta, MapperBolsillo mapperBolsillo, MapperTransaccion mapperTransaccion) {
        this.mapperCuenta = mapperCuenta;
        this.mapperBolsillo = mapperBolsillo;
        this.mapperTransaccion = mapperTransaccion;

        this.repository = new CuentaImplementacion(cuentaJPARepository,mapperCuenta);
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository,mapperBolsillo);
        this.transaccionRepository = new TransaccionImplementacion(transaccionJPARepository, mapperTransaccion);

        this.cuentaServices = new CuentaServices(this.repository);
        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);
        this.transaccionServices = new TransaccionServices(this.transaccionRepository);
    }


    @Operation(summary = "Página de inicio", description = "Muestra la página de inicio con la información de la cuenta y los bolsillos del usuario logeado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de inicio mostrada correctamente", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Información cuenta</h1></body></html>"))),
            @ApiResponse(responseCode = "401", description = "No se ha iniciado sesión", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Iniciar sesión</h1></body></html>"))),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Acceso denegado</h1></body></html>"))),
    })
    @GetMapping({"/home","/"})
    public String home( Model model,HttpServletRequest request, @Parameter(description = "Mensaje a mostrar en la página de inicio", example = "Cuenta creada correctamente") @ModelAttribute("mensaje") String mensajeRecibido) {

        Boolean isCuentaCreada = false;
        Boolean isBolsilloCreado = false;

        InformationUsuarioModel(model,request); // Se obtiene el nombre  yla imgdel usuario que inició sesión y lo guarda eel model

        CuentaDto cuentaDto = new CuentaDto();
        BolsilloDto bolsilloDto = new BolsilloDto();
        
        try{
            Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
            Integer idUsuarioLogueado = usuarioLogeado.getId();
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(idUsuarioLogueado); // Se obtiene la cuenta del usuario logeado

            // Se calcula el saldo disponible del usuario logeado
            Double saldoDisponible = cuenta.getSaldoActual(); // descontar los depositos a bolsillos

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


    @Operation(summary = "Crear una nueva cuenta", description = "Crea una nueva cuenta bancaria para el usuario logeado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redireccionamiento exitoso a la página de inicio",content = @Content(mediaType = "text/html", examples  = @ExampleObject(name = "Creación exitosa", value = "<html><body>Redireccionando a la página de inicio...</body></html>"))),

            @ApiResponse(responseCode = "400", description = "Error al crear la cuenta", content = @Content(mediaType = "text/html", examples = {
                    @ExampleObject(name = "Error en la creación", value = "<html><body>Redireccionando a la página de inicio con un mensaje de error...</body></html>")
            }))
    })
    @PostMapping("/crearCuenta")
    public String crearCuenta( @Parameter(description = "Datos de la nueva cuenta a crear") CuentaDto cuentaDto,RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Usuario usuarioLogeado = getUsuarioLogeado(request); // Se obtiene el usuario que inició sesión
        cuentaDto.setUsuarioId(usuarioLogeado);
        cuentaDto.setSaldoActual(cuentaDto.getSaldo());
        cuentaDto.setFechaCreacion(new Date());
        cuentaDto.setTipoCuenta("Ahorros");
        cuentaDto.setCvc(generarCvc());
        System.out.println(cuentaDto.toString()    );


        Cuenta cuenta = mapperCuenta.CuentaDtoToCuentaDomain(cuentaDto);
        boolean guardado = cuentaServices.saveOrUpdateCuenta(cuenta); // Se guarda la cuenta en la base de datos

        if(guardado){
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

    public int generarCvc() {
        Random random = new Random();
        int cvc = random.nextInt(900) + 100; // Genera un número aleatorio entre 100 y 999
        return cvc;
    }




}
