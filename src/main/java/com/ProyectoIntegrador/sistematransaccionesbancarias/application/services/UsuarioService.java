package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.UsuarioRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.getAllUsuarios();
    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.getUsuarioById(id);
    }

    public  boolean createUsuario(Usuario usuario){
        try {
            usuarioRepository.getUsuarioById(usuario.getId());
            return false; // Ya existe un usuario con ese ID
        } catch (UserNotFoundException ex) {
            // Si se lanza una excepción UserNotFoundException, significa que no se encontró ningún usuario con el ID especificado
            // Por lo tanto, podemos registrar un nuevo usuario con ese ID
            usuarioRepository.createUsuario(usuario);
            return true;
        }

    }

    public boolean UpdateUsuario(Usuario usuario) {
        usuarioRepository.UpdateUsuario(usuario);
        if (usuarioRepository.getUsuarioById(usuario.getId()) != null) // si al buscar el usuario con el id del usuario, entonces se creó o actualizó correctamente
            return true;
        return true;
    }

    public boolean deleteUsuarioById(Integer id) {
        usuarioRepository.deleteUsuarioById(id); // elimina el usuario
        if (usuarioRepository.getUsuarioById(id) == null) { // si al buscar el usuario con el id eliminado no existe, entonces se eliminó correctamente
            return true;
        }
        return false;
    }



}
