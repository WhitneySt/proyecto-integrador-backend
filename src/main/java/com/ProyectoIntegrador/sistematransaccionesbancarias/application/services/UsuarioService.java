package com.ProyectoIntegrador.sistematransaccionesbancarias.application.services;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.UsuarioRepository;

import java.util.List;

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

    public boolean saveOrUpdateUsuario(Usuario usuario) {
        usuarioRepository.saveOrUpdateUsuario(usuario);
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
