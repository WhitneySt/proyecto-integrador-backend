package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.BolsilloServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.TipoTransaccionServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.TransaccionServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.BolsilloDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TransaccionDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion.TipoTransaccionImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion.TipoTransaccionJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion.TransaccionImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion.TransaccionJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperBolsillo;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperCuenta;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTipoTransaccion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperTransaccion;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;
import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.HomeController.InformationUsuarioModel;


@Controller
@Tag(name = "Transacciones", description = "Endpoints para gestionar transacciones bancarias")
public class TransaccionController {

    @Autowired
    MapperTransaccion mapperTransaccion;
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    MapperTipoTransaccion mapperTipoTransaccion;

    TransaccionServices transaccionServices;
    CuentaServices cuentaServices;
    BolsilloServices bolsilloServices;
    TipoTransaccionServices tipoTransaccionServices;

    TransaccionImplementacion transaccionRepository;
    CuentaImplementacion cuentaRepository;
    BolsilloImplementacion bolsilloRepository;
    TipoTransaccionImplementacion tipoTransaccionRepository;

    @Autowired
    public TransaccionController(TransaccionJPARepository transaccionJPARepository, MapperTransaccion mapperTransaccion, CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta, BolsilloJPARepository bolsilloJPARepository, MapperBolsillo mapperBolsillo, TipoTransaccionJPARepository tipoTransaccionJPARepository, MapperTipoTransaccion mapperTipoTransaccion) {
        this.mapperTransaccion = mapperTransaccion;
        this.mapperCuenta = mapperCuenta;
        this.mapperBolsillo = mapperBolsillo;
        this.mapperTipoTransaccion = mapperTipoTransaccion;

        this.transaccionRepository = new TransaccionImplementacion(transaccionJPARepository, mapperTransaccion);
        this.cuentaRepository = new CuentaImplementacion(cuentaJPARepository, mapperCuenta);
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository, mapperBolsillo);
        this.tipoTransaccionRepository = new TipoTransaccionImplementacion(tipoTransaccionJPARepository, mapperTipoTransaccion);

        this.transaccionServices = new TransaccionServices(this.transaccionRepository);
        this.cuentaServices = new CuentaServices(this.cuentaRepository);
        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);
        this.tipoTransaccionServices = new TipoTransaccionServices(this.tipoTransaccionRepository);
    }

    @GetMapping("/transaccion")
    @Operation(summary = "Página de transacciones", description = "Accede a la página de transacciones del usuario logeado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acceso exitoso",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Transacciones", value = "<html><body><h1>Página de Transacciones</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "401", description = "No se ha iniciado sesión", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Iniciar sesión</h1></body></html>"))),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Acceso denegado</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String transaccion(Model model, HttpServletRequest request) {
        List<Bolsillo> listaBolsillos = null;
        List<Transaccion> transacciones = null;
        TransaccionDto transaccionDto = new TransaccionDto();

        InformationUsuarioModel(model,request);

        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Integer usuarioId = usuarioLogeado.getId();
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioId);
            listaBolsillos = bolsilloServices.getAllBolsillosByCuenta(cuenta.getId());
            transacciones = transaccionServices.getAllTransaccionesByUsuario(usuarioId);
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            model.addAttribute("bolsillos", listaBolsillos);
            model.addAttribute("transacciones", transacciones);
            model.addAttribute("depositoDto", transaccionDto);
            model.addAttribute("transferenciaDto", transaccionDto);
            model.addAttribute("retiroDto", transaccionDto);
        }

        return "transaccion/transaccion";
    }

    @PostMapping("/crearTransaccion/transferencia")
    @Operation(summary = "Crear una transferencia", description = "Crea una nueva transferencia entre cuentas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferencia creada correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Transferencia", value = "<html><body><h1>Transferencia creada correctamente</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Solicitud incorrecta</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
})
    public String crearTransferencia(TransaccionDto transaccionDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            //Usuario usuarioLogeado = getUsuarioLogeado(request);
            //Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());

            //transaccionDto.setIdCuentaOrigen(cuenta.getId());
            //     Transaccion transaccion = mapperBolsillo.BolsilloDtoToBolsilloDomain(bolsilloDto);
            //     boolean guardar = bolsilloServices.saveOrUpdateBolsillo(bolsillo);

            //     if(guardar){
            //         System.out.println("Se guardo el bolsillo");
            redirectAttributes.addFlashAttribute("mensaje","createOk");
            //     } else {
            //         System.out.println("No se guardo el bolsillo");
            //         redirectAttributes.addFlashAttribute("mensaje","createError");
            //     }
        } catch(CuentaNotFoundException e){
            //     redirectAttributes.addFlashAttribute("mensaje","cuenta-null");
            //     return "redirect:/";
        }
        return "redirect:/transaccion";
    }

    @PostMapping("/crearTransaccion/deposito")
    @Operation(summary = "Crear un depósito", description = "Realiza un depósito en la cuenta del usuario logeado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito realizado correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Depósito", value = "<html><body><h1>Depósito realizado correctamente</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Solicitud incorrecta</h1></body></html>"))),
            @ApiResponse(responseCode = "404", description = "Tipo de transacción no encontrado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Tipo de transacción no encontrado</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String crearDeposito(TransaccionDto transaccionDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());
            List<TipoTransaccion> listaTipoTransaccion = tipoTransaccionServices.getAllTipoTransaccion();

            transaccionDto.setIdCuentaDestino(cuenta);
            for (int i = 0; i < listaTipoTransaccion.size(); i++) {
                TipoTransaccion tipoTransaccion = listaTipoTransaccion.get(i);
                if(tipoTransaccion.getNombre().equalsIgnoreCase("deposito")){
                    transaccionDto.setIdTipoTransaccion(tipoTransaccion);
                    break;
                }
            }

            transaccionDto.setFechaTransaccion(new Date());
            transaccionDto.setUsuarioId(usuarioLogeado);
            Transaccion transaccion = mapperTransaccion.TransaccionDtoToTransaccionDomain(transaccionDto);
            boolean guardar = transaccionServices.saveOrUpdateTransaccion(transaccion);

            if(guardar){
                //         System.out.println("Depósito realizado");
                redirectAttributes.addFlashAttribute("mensaje","createOk");
            } else {
                System.out.println("Depósito no realizado");
                redirectAttributes.addFlashAttribute("mensaje","createError");}
        } catch(CuentaNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje","cuenta-null");
            return "redirect:/";
        }
        return "redirect:/transaccion";
    }

    @PostMapping("/crearTransaccion/retiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retiro realizado correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Retiro", value = "<html><body><h1>Retiro realizado correctamente</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Solicitud incorrecta</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String crearRetiro(BolsilloDto bolsilloDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // try {
        //     Usuario usuarioLogeado = getUsuarioLogeado(request);
        //     Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());

        //     bolsilloDto.setIdCuenta(cuenta);
        //     Bolsillo bolsillo = mapperBolsillo.BolsilloDtoToBolsilloDomain(bolsilloDto);
        //     boolean guardar = bolsilloServices.saveOrUpdateBolsillo(bolsillo);

        //     if(guardar){
        //         System.out.println("Se guardo el bolsillo");
        redirectAttributes.addFlashAttribute("mensaje","createOk");
        //     } else {
        //         System.out.println("No se guardo el bolsillo");
        //         redirectAttributes.addFlashAttribute("mensaje","createError");
        //     }
        // } catch(CuentaNotFoundException e){
        //     redirectAttributes.addFlashAttribute("mensaje","cuenta-null");
        //     return "redirect:/";
        // }
        return "redirect:/transaccion";
    }
}
