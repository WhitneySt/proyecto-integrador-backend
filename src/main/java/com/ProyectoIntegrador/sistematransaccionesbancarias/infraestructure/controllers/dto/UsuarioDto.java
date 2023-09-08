package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.controllers.dto;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;

import java.sql.Date;

public class UsuarioDto {


    private Integer id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Date fechaCreacion;
    private String urlImage;
    private Rol rol;
    private Estado estado;

    public UsuarioDto() {

        this.fechaCreacion = new Date(System.currentTimeMillis());
        this.rol = new Rol(1, "Usuario");
        this.estado = new Estado(1, "Activo");
        System.out.println(fechaCreacion) ;
    }

    public UsuarioDto(Integer id, String nombre, String apellido, String correo, String contrasena, Date fechaCreacion, String urlImage) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaCreacion = fechaCreacion;
        this.urlImage = urlImage;

        // ? Valores por defecto
        this.rol = new Rol(1, "Usuario");
        this.estado = new Estado(1, "Activo");
    }

    public Integer getId() {
        return id;
    }

    public  void setId(Integer id){
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "UsuarioDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", urlImage='" + urlImage + '\'' +
                ", rol=" + rol +
                ", estado=" + estado +
                '}';
    }
}
