package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;

import java.util.List;

public interface UsuarioRepository {

    public List<Usuario>  getAllUsuarios();
    public Usuario getUsuarioById(Integer id);
    public boolean createUsuario(Usuario usuario);
    public boolean  UpdateUsuario(Usuario usuario);

    public boolean deleteUsuarioById(Integer id);


}
