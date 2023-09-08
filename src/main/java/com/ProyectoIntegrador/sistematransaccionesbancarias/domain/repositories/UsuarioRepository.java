package com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;

import java.util.List;

public interface UsuarioRepository {

    public List<Usuario>  getAllUsuarios();
    public Usuario getUsuarioById(Integer id);
    public boolean  saveOrUpdateUsuario(Usuario usuario); // si existe lo actualiza, si no existe lo crea, esto se sabe por el id del usuario y retorna el usuario creado o actualizado
    public boolean deleteUsuarioById(Integer id);


}
