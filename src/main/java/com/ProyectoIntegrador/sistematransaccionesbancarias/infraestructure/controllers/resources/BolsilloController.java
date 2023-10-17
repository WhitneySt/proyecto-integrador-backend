package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.BolsilloServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.CuentaServices;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Bolsillo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.resources.Controller.getUsuarioLogeado;


@Controller
public class BolsilloController {

    @Autowired
    MapperCuenta mapperCuenta;
    MapperBolsillo mapperBolsillo;
    CuentaServices cuentaServices;
    CuentaImplementacion cuentaRepository;
    BolsilloServices bolsilloServices;
    BolsilloImplementacion bolsilloRepository;

    @Autowired
    public BolsilloController(BolsilloJPARepository bolsilloJPARepository, MapperBolsillo mapperBolsillo, CuentaJPARepository cuentaJPARepository, MapperCuenta mapperCuenta) {
        this.mapperBolsillo = mapperBolsillo;
        this.cuentaRepository = new CuentaImplementacion(cuentaJPARepository, mapperCuenta);
        this.bolsilloRepository = new BolsilloImplementacion(bolsilloJPARepository, mapperBolsillo);
        this.bolsilloServices = new BolsilloServices(this.bolsilloRepository);
        this.cuentaServices = new CuentaServices(this.cuentaRepository);
    }

    @GetMapping("/bolsillos")
    public String bolsillos(Model model, HttpServletRequest request) {

        InformationUsuarioModel(model,request);
        BolsilloDto bolsilloDto = new BolsilloDto();
        List<Bolsillo> listaBolsillos = null;

        try {
            InformationUsuarioModel(model,request);
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());
            bolsilloDto.setColor("#ffffff");

             listaBolsillos = bolsilloServices.getAllBolsillosByCuenta(cuenta.getId());
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            model.addAttribute("bolsillos", listaBolsillos);
            model.addAttribute("bolsilloDto", bolsilloDto);
        }

        return "bolsillos/bolsillos";
    }

    @GetMapping("/bolsillos/{id}")
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
    public String deleteBolsillo(Model model, @PathVariable Integer id) {
        bolsilloServices.deleteBolsilloById(id);
        return "redirect:/bolsillos";
    }

    @PostMapping("/crearBolsillo")
    public String crearBolsillo(BolsilloDto bolsilloDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());

            bolsilloDto.setIdCuenta(cuenta);
            Bolsillo bolsillo = mapperBolsillo.BolsilloDtoToBolsilloDomain(bolsilloDto);
            boolean guardar = bolsilloServices.saveOrUpdateBolsillo(bolsillo);

            if(guardar){
                System.out.println("Se guardo el bolsillo");
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

    @PostMapping("/editarBolsillo")
    public String editarBolsillo(BolsilloDto bolsilloDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Usuario usuarioLogeado = getUsuarioLogeado(request);
            Cuenta cuenta = cuentaServices.getCuentaByIdUsuario(usuarioLogeado.getId());

            bolsilloDto.setIdCuenta(cuenta);
            Bolsillo bolsillo = mapperBolsillo.BolsilloDtoToBolsilloDomain(bolsilloDto);
            boolean guardar = bolsilloServices.saveOrUpdateBolsillo(bolsillo);

            if(guardar){
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
