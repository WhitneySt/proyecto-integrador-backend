package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.BolsilloDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.CuentaDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.TransaccionDto;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.bolsillo.BolsilloJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.cuenta.CuentaJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoMovimiento.TipoMovimientoImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoMovimiento.TipoMovimientoJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion.TipoTransaccionImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.tipoTransaccion.TipoTransaccionJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion.TransaccionImplementacion;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.transaccion.TransaccionJPARepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.CuentaNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.*;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
@Tag(name = "Bolsillos", description = "Endpoints para gestionar bolsillos de usuarios")
public class BolsilloController {

    @Autowired
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    MapperTransaccion mapperTransaccion;
    MapperTipoTransaccion mapperTipoTransaccion;
    MapperTipoMovimiento mapperTipoMovimiento;

    CuentaServices cuentaServices;
    TransaccionServices transaccionServices;
    TipoTransaccionServices tipoTransaccionServices;
    BolsilloServices bolsilloServices;
    TipoMovimientoServices tipoMovimientoServices;

    CuentaImplementacion cuentaRepository;
    BolsilloImplementacion bolsilloRepository;
    TransaccionImplementacion transaccionRepository;
    TipoTransaccionImplementacion tipoTransaccionRepository;
    TipoMovimientoImplementacion tipoMovimientoRepository;

    @Autowired
    public BolsilloController(BolsilloJPARepository bolsilloJPARepository, MapperBolsillo mapperBolsillo, CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta, TransaccionJPARepository transaccionJPARepository, MapperTransaccion mapperTransaccion, TipoTransaccionJPARepository tipoTransaccionJPARepository, MapperTipoTransaccion mapperTipoTransaccion, TipoMovimientoJPARepository tipoMovimientoJPARepository, MapperTipoMovimiento mapperTipoMovimiento) {
        this.mapperBolsillo = mapperBolsillo;
        this.mapperTransaccion = mapperTransaccion;
        this.mapperTipoTransaccion = mapperTipoTransaccion;
        this.mapperTipoMovimiento = mapperTipoMovimiento;

        this.cuentaRepository = new CuentaImplementacion(cuentaJPARepository, mapperCuenta);
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository, mapperBolsillo);
        this.transaccionRepository = new TransaccionImplementacion(transaccionJPARepository, mapperTransaccion);
        this.tipoTransaccionRepository = new TipoTransaccionImplementacion(tipoTransaccionJPARepository, mapperTipoTransaccion);
        this.tipoMovimientoRepository = new TipoMovimientoImplementacion(tipoMovimientoJPARepository, mapperTipoMovimiento);

        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);
        this.cuentaServices = new CuentaServices(this.cuentaRepository);
        this.transaccionServices = new TransaccionServices(this.transaccionRepository);
        this.tipoTransaccionServices = new TipoTransaccionServices(this.tipoTransaccionRepository);
        this.tipoMovimientoServices = new TipoMovimientoServices(this.tipoMovimientoRepository);
    }

    @GetMapping("/bolsillos")
    @Operation(summary = "Obtener lista de bolsillos", description = "Obtiene la lista de bolsillos de un usuario logeado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bolsillos obtenidos correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Bolsillos", value = "<html><body><h1>Lista de Bolsillos</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "401", description = "No se ha iniciado sesión", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Iniciar sesión</h1></body></html>"))),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Acceso denegado</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                            })
    public String bolsillos(Model model, HttpServletRequest request) {
        BolsilloDto bolsilloDto = new BolsilloDto();
        List<Bolsillo> listaBolsillos = null;

        try {
            InformationUsuarioModel(model,request);
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());
            bolsilloDto.setColor("#ffffff");

             listaBolsillos = bolsilloServices.getAllBolsillosByCuenta(cuenta.getId()).stream().filter(Bolsillo::getStatus).toList();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            model.addAttribute("bolsillos", listaBolsillos);
            model.addAttribute("bolsilloDto", bolsilloDto);
        }

        return "bolsillos/bolsillos";
    }

    @GetMapping("/bolsillos/{id}")
    @Operation(summary = "Obtener bolsillo por ID", description = "Obtiene un bolsillo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bolsillo obtenido correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Bolsillo", value = "<html><body><h1>Detalles del Bolsillo</h1></body></html>")
                    })),
                    @ApiResponse(responseCode = "404", description = "Bolsillo no encontrado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Bolsillo no encontrado</h1></body></html>"))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String bolsillos(Model model, @PathVariable Integer id) {
        BolsilloDto bolsilloDto = new BolsilloDto();

        List<Bolsillo> listaBolsillos = bolsilloServices.getAllBolsillos();
        if(id != null || id > 0) {
            //find bolsillo
            for (int i = 0; i < listaBolsillos.size(); i++) {
                Bolsillo bolsillo = listaBolsillos.get(i);
                Integer idBolsillo = bolsillo.getId();
                if(idBolsillo.intValue() == id.intValue()) {
                    bolsilloDto = mapperBolsillo.BolsilloDomainToBolsilloDto(bolsillo);
                    break;
                }
            }
        }

        model.addAttribute("bolsilloDto", bolsilloDto);

        return "bolsillos/editar";
    }

    @GetMapping("/bolsillos/remove/{id}")
    @Operation(summary = "Eliminar bolsillo por ID", description = "Elimina un bolsillo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bolsillo eliminado correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Bolsillo", value = "<html><body><h1>Bolsillo eliminado correctamente</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "404", description = "Bolsillo no encontrado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Bolsillo no encontrado</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String deleteBolsillo(Model model, @PathVariable Integer id, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Usuario usuarioLogeado = getUsuarioLogeado(request);
        Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());
        Bolsillo bolsillo = bolsilloServices.getBolsilloById(id);
        bolsillo.setStatus(false);
        bolsillo.setSaldo(0.0);

        Double saldoBolsillo = bolsillo.getSaldo();

        Double nuevoSaldoCuenta = cuenta.getSaldoActual() + saldoBolsillo;
        cuenta.setSaldoActual(nuevoSaldoCuenta);
        cuenta.setFechaActualizacion(new Date());

        cuentaServices.saveOrUpdateCuenta(cuenta);
        Bolsillo bolsilloCreado = bolsilloServices.saveOrUpdateBolsillo(bolsillo);
        bolsillo.setId(bolsilloCreado.getId());

        TransaccionDto transaccionDto = new TransaccionDto();
        TipoTransaccion _tipoTransaccion = tipoTransaccionServices.getTipoTransaccionByName("Devolucion");
        transaccionDto.setIdTipoTransaccion(_tipoTransaccion);
        transaccionDto.setFechaTransaccion(new Date());
        transaccionDto.setUsuarioId(usuarioLogeado);
        transaccionDto.setIdBolsilloOrigen(bolsillo);
        transaccionDto.setIdCuentaDestino(cuenta);
        transaccionDto.setMonto(saldoBolsillo);
        transaccionDto.setDescripcion("ELIMINACION DE BOLSILLO: " + bolsilloCreado.getNombre());
        Transaccion transaccion = mapperTransaccion.TransaccionDtoToTransaccionDomain(transaccionDto);
        transaccionRepository.saveOrUpdateTransaccion(transaccion);

        return "redirect:/bolsillos";
    }

    @PostMapping("/crearBolsillo")
    @Operation(summary = "Crear un bolsillo", description = "Crea un nuevo bolsillo para el usuario logeado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bolsillo creado correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Bolsillo", value = "<html><body><h1>Bolsillo creado correctamente</h1></body></html>")
                    })),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Solicitud incorrecta</h1></body></html>"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String crearBolsillo(BolsilloDto bolsilloDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());

            Double montoBolsillo =  bolsilloDto.getSaldo();

            bolsilloDto.setIdCuenta(cuenta);
            bolsilloDto.setStatus(true);
            Double nuevoSaldoCuenta = cuenta.getSaldoActual() - montoBolsillo;
            cuenta.setSaldoActual(nuevoSaldoCuenta);
            cuenta.setFechaActualizacion(new Date());

            if(nuevoSaldoCuenta < 0 || montoBolsillo <= 0) {
                redirectAttributes.addFlashAttribute("mensaje","createError");
                return "redirect:/bolsillos";
            }

            Bolsillo bolsillo = mapperBolsillo.BolsilloDtoToBolsilloDomain(bolsilloDto);
            cuentaServices.saveOrUpdateCuenta(cuenta);
            Bolsillo bolsilloCreado = bolsilloServices.saveOrUpdateBolsillo(bolsillo);
            bolsillo.setId(bolsilloCreado.getId());

            TransaccionDto transaccionDto = new TransaccionDto();
            TipoTransaccion _tipoTransaccion = tipoTransaccionServices.getTipoTransaccionByName("Transferencia");
            transaccionDto.setIdTipoTransaccion(_tipoTransaccion);
            TipoMovimiento tipoMovimiento = tipoMovimientoServices.getTipoMovimientoByCodes("CU","BO");
            transaccionDto.setIdTipoMovimiento(tipoMovimiento);
            transaccionDto.setFechaTransaccion(new Date());
            transaccionDto.setUsuarioId(usuarioLogeado);
            transaccionDto.setIdBolsilloDestino(bolsillo);
            transaccionDto.setMonto(montoBolsillo);
            transaccionDto.setDescripcion("CREACION DE BOLSILLO: " + bolsilloCreado.getNombre());
            Transaccion transaccion = mapperTransaccion.TransaccionDtoToTransaccionDomain(transaccionDto);
            boolean guardado = transaccionRepository.saveOrUpdateTransaccion(transaccion);

            if(guardado){
                redirectAttributes.addFlashAttribute("mensaje","createOk");
            } else {
                redirectAttributes.addFlashAttribute("mensaje","createError");
            }
        } catch(CuentaNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje","cuenta-null");
            return "redirect:/";
        }
        return "redirect:/bolsillos";
    }

    @PostMapping("/editarBolsillo")
    @Operation(summary = "Editar un bolsillo", description = "Edita un bolsillo existente para el usuario logeado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bolsillo editado correctamente",
                    content = @Content(mediaType = "text/html", examples = {
                            @ExampleObject(name = "Bolsillo", value = "<html><body><h1>Bolsillo editado correctamente</h1></body></html>")
                    })),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Solicitud incorrecta</h1></body></html>"))),
                    @ApiResponse(responseCode = "404", description = "Bolsillo no encontrado", content = @Content(mediaType = "text/html", examples = @ExampleObject(value = "<html><body><h1>Bolsillo no encontrado</h1></body></html>"))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String editarBolsillo(BolsilloDto bolsilloDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());

            bolsilloDto.setIdCuenta(cuenta);
            Bolsillo bolsillo = mapperBolsillo.BolsilloDtoToBolsilloDomain(bolsilloDto);
            Bolsillo guardado = bolsilloServices.saveOrUpdateBolsillo(bolsillo);

            if(guardado != null){
                System.out.println("Se edito el bolsillo");
                redirectAttributes.addFlashAttribute("mensaje","createOk");
            } else {
                System.out.println("No se guardo el bolsillo");
                redirectAttributes.addFlashAttribute("mensaje","createError");
            }
        } catch(CuentaNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje","cuenta-null");
            return "redirect:/";
        }
        return "redirect:/bolsillos";
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
