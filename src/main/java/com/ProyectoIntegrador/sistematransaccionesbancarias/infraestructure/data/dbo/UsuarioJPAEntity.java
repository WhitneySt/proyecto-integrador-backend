package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Usuarios")
public class UsuarioJPAEntity {

    @Id
    
    private Integer id;
    @Column(length = 50, nullable = false)
    private String nombre;
    @Column(length = 255, nullable = false)
    private String correo; // El c
    @Column(length = 200, nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Date fechaCreacion;
    private String urlImage;
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolJPAEntity rol;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private EstadoJPAEntity estado;
    public UsuarioJPAEntity() {
    }

    public UsuarioJPAEntity(Integer id, String nombre, String correo, String contrasena, Date fechaCreacion, String urlImage) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaCreacion = fechaCreacion;
        this.urlImage = urlImage;

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

    public RolJPAEntity getRol() {
        return rol;
    }

    public void setRol(RolJPAEntity rol) {
        this.rol = rol;
    }

    public EstadoJPAEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoJPAEntity estado) {
        this.estado = estado;
    }
}
