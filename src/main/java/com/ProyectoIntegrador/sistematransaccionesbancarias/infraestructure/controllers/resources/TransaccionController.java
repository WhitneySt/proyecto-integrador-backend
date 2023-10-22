package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto.BolsilloDto;
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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
public class TransaccionController {

    @Autowired
    MapperTransaccion mapperTransaccion;
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    MapperTipoTransaccion mapperTipoTransaccion;
    MapperTipoMovimiento mapperTipoMovimiento;

    TransaccionServices transaccionServices;
    CuentaServices cuentaServices;
    BolsilloServices bolsilloServices;
    TipoTransaccionServices tipoTransaccionServices;
    TipoMovimientoServices tipoMovimientoServices;

    TransaccionImplementacion transaccionRepository;
    CuentaImplementacion cuentaRepository;
    BolsilloImplementacion bolsilloRepository;
    TipoTransaccionImplementacion tipoTransaccionRepository;
    TipoMovimientoImplementacion tipoMovimientoRepository;

    @Autowired
    public TransaccionController(TransaccionJPARepository transaccionJPARepository, MapperTransaccion mapperTransaccion, CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta, BolsilloJPARepository bolsilloJPARepository, MapperBolsillo mapperBolsillo, TipoTransaccionJPARepository tipoTransaccionJPARepository, MapperTipoTransaccion mapperTipoTransaccion, TipoMovimientoJPARepository tipoMovimientoJPARepository, MapperTipoMovimiento mapperTipoMovimiento) {
        this.mapperTransaccion = mapperTransaccion;
        this.mapperCuenta = mapperCuenta;
        this.mapperBolsillo = mapperBolsillo;
        this.mapperTipoTransaccion = mapperTipoTransaccion;
        this.mapperTipoMovimiento = mapperTipoMovimiento;

        this.transaccionRepository = new TransaccionImplementacion(transaccionJPARepository, mapperTransaccion);
        this.cuentaRepository = new CuentaImplementacion(cuentaJPARepository, mapperCuenta);
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository, mapperBolsillo);
        this.tipoTransaccionRepository = new TipoTransaccionImplementacion(tipoTransaccionJPARepository, mapperTipoTransaccion);
        this.tipoMovimientoRepository = new TipoMovimientoImplementacion(tipoMovimientoJPARepository, mapperTipoMovimiento);

        this.transaccionServices = new TransaccionServices(this.transaccionRepository);
        this.cuentaServices = new CuentaServices(this.cuentaRepository);
        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);
        this.tipoTransaccionServices = new TipoTransaccionServices(this.tipoTransaccionRepository);
        this.tipoMovimientoServices = new TipoMovimientoServices(this.tipoMovimientoRepository);
    }

    @GetMapping("/transaccion")
    public String transaccion(Model model, HttpServletRequest request) {
        List<Bolsillo> listaBolsillos = null;
        List<Transaccion> transacciones = null;
        List<TipoTransaccion> tipoTransaccion = null;
        TransaccionDto transaccionDto = new TransaccionDto();

        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Integer usuarioId = usuarioLogeado.getId();
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioId);
            listaBolsillos = bolsilloServices.getAllBolsillosByCuenta(cuenta.getId());
            transacciones = transaccionServices.getAllTransaccionesByUsuario(usuarioId);
            tipoTransaccion = tipoTransaccionServices.getAllTipoTransaccion();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            model.addAttribute("bolsillos", listaBolsillos);
            model.addAttribute("transacciones", transacciones);
            model.addAttribute("tipoTransacciones", tipoTransaccion);
            model.addAttribute("depositoDto", transaccionDto);
            model.addAttribute("transferenciaDto", transaccionDto);
            model.addAttribute("retiroDto", transaccionDto);
        }

        return "transaccion/transaccion";
    }

    @PostMapping("/crearTransaccion/{tipoTransaccion}")
    public String crearTransaccion(@PathVariable String tipoTransaccion, TransaccionDto transaccionDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            boolean guardado = transaccionServices.saveOrUpdateTransaccion(cuentaServices, tipoTransaccionServices, bolsilloServices, tipoMovimientoServices,  mapperTransaccion, mapperTipoMovimiento, transaccionDto, usuarioLogeado, tipoTransaccion);

            if(guardado){
                redirectAttributes.addFlashAttribute("mensaje","createOk");
            } else {
                redirectAttributes.addFlashAttribute("mensaje","createError");
            }
        } catch(CuentaNotFoundException e){
            redirectAttributes.addFlashAttribute("mensaje","cuenta-null");
        }
        return "redirect:/transaccion";
    }
}
