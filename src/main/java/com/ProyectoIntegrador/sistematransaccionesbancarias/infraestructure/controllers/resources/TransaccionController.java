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
    public String transaccion(Model model, HttpServletRequest request) {
        List<Bolsillo> listaBolsillos = null;
        List<Transaccion> transacciones = null;
        TransaccionDto transaccionDto = new TransaccionDto();

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
