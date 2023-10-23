package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.*;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.*;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

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
    @GetMapping("/transaccion/{id}")
    public String verDetalleTransaccion(@PathVariable Integer id, Model model, HttpServletRequest request){
        Usuario usuarioLogeado = getUsuarioLogeado(request);
        Integer usuarioId = usuarioLogeado.getId();
        Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioId);

        Transaccion detalleTransaccion = transaccionServices.getTransaccionById(id);
        TransaccionDto transaccionDto = mapperTransaccion.TransaccionDomainToTransaccionDto(detalleTransaccion);
        String idCuenta = cuenta.getId().toString();

        String tipoTransaccion = detalleTransaccion.getIdTipoTransaccion().getNombre();
        String codigoOrigen = "N/A";
        String codigoDestino = "N/A";
        if(detalleTransaccion.getIdTipoMovimiento() != null) {
            codigoOrigen = detalleTransaccion.getIdTipoMovimiento().getCodigoOrigen().equalsIgnoreCase("CU") ? "Cuenta" : "Bolsillo";
            codigoDestino = detalleTransaccion.getIdTipoMovimiento().getCodigoDestino().equalsIgnoreCase("CU") ? "Cuenta" : "Bolsillo";
        }

        if(tipoTransaccion.equalsIgnoreCase("Transferencia")) {
            if(detalleTransaccion.getCuentaTerceros() != null && detalleTransaccion.getIdBolsilloDestino() == null && detalleTransaccion.getIdBolsilloOrigen() == null) { // Transferido de mi cuenta a otra cuenta
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(idCuenta);
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(detalleTransaccion.getCuentaTerceros());
            } else if(detalleTransaccion.getCuentaTerceros() == null && detalleTransaccion.getIdBolsilloDestino() != null && detalleTransaccion.getIdBolsilloOrigen() == null) { // Transferido de mi cuenta a un bolsillo
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(idCuenta);
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(detalleTransaccion.getIdBolsilloDestino().getNombre());
            } else if(detalleTransaccion.getIdBolsilloOrigen() != null && detalleTransaccion.getIdBolsilloDestino() != null) { // Transferido de un bolsillo a otro
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(detalleTransaccion.getIdBolsilloOrigen().getNombre());
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(detalleTransaccion.getIdBolsilloDestino().getNombre());
            } else if(detalleTransaccion.getIdBolsilloOrigen() != null && detalleTransaccion.getCuentaTerceros() == null && detalleTransaccion.getIdBolsilloDestino() == null) { // Transferido de un bolsillo a mi cuenta
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(detalleTransaccion.getIdBolsilloOrigen().getNombre());
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(idCuenta);
            } else if(detalleTransaccion.getIdBolsilloOrigen() != null && detalleTransaccion.getCuentaTerceros() != null && !detalleTransaccion.getCuentaTerceros().isEmpty() && detalleTransaccion.getIdBolsilloDestino() == null) { // Transferido de un bolsillo a una cuenta de terceros
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(detalleTransaccion.getIdBolsilloOrigen().getNombre());
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(detalleTransaccion.getCuentaTerceros());
            } else if(detalleTransaccion.getIdBolsilloOrigen() != null && detalleTransaccion.getIdCuentaDestino() != null) { // Transferido de un bolsillo a una cuenta
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(detalleTransaccion.getIdBolsilloOrigen().getNombre());
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(detalleTransaccion.getIdCuentaDestino().getId().toString());
            } else if(detalleTransaccion.getIdCuentaOrigen() != null && detalleTransaccion.getIdCuentaDestino() != null) { // Transferido de un bolsillo a una cuenta
                transaccionDto.setProductoOrigen(codigoOrigen);
                transaccionDto.setNumeroProductoOrigen(detalleTransaccion.getIdCuentaOrigen().getId().toString());
                transaccionDto.setProductoDestino(codigoDestino);
                transaccionDto.setNumeroProductoDestino(detalleTransaccion.getIdCuentaDestino().getId().toString());
            }
        } else {
            // Deposito & Retiro
            transaccionDto.setProductoOrigen(codigoOrigen);
            transaccionDto.setNumeroProductoOrigen(detalleTransaccion.getUsuarioId().getNombre());
            transaccionDto.setProductoDestino(codigoDestino);
            transaccionDto.setNumeroProductoDestino(idCuenta);
        }

        model.addAttribute("transaccion", transaccionDto);

        return "transaccion/seeDetailsMovimientos";
    }
}
