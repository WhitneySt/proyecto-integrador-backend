package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.UsuarioRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.UserNotFoundException;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioImplementacion implements UsuarioRepository {

    // ?  Inyección de dependencias
    private  final UsuarioJPARepository usuarioJPARepository;
    private final MapperUsuario mapperUsuario;

    public UsuarioImplementacion(UsuarioJPARepository usuarioJPARepository, MapperUsuario mapperUsuario) {
        this.usuarioJPARepository = usuarioJPARepository;
        this.mapperUsuario = mapperUsuario;

    }

    @Override
    public List<Usuario> getAllUsuarios() {

        List<Usuario> usuariosList = new ArrayList<>();
        // obtener todos los usuarios de la base de datos y los  guardar en la lista usuarios, se tiene que hacer el mapeo de la entidad de la base de datos a la entidad de dominio
        //Regresa un objeto iterable, se puede acceder a cada uno de los usuarios y agregarlos a la lista

        usuarioJPARepository.findAll().forEach(Usuario -> usuariosList.add(mapperUsuario.UsuarioJPAToUsuarioDomain(Usuario)));

        return usuariosList;
    }

    @Override
    public Usuario getUsuarioById(Integer id) {

        // obtengo el usuario de la base de datos
        Optional<UsuarioJPAEntity> optionalUsuario = usuarioJPARepository.findById(id);

        if (optionalUsuario.isPresent()) {
            return mapperUsuario.UsuarioJPAToUsuarioDomain(optionalUsuario.get()); // se tiene que hacer el mapeo de la entidad de la base de datos a la entidad de dominio para que se retorne el tipo de valor correcto
        }
        else {
                // Manejo del caso en que no se encuentre el usuario
            throw new UserNotFoundException("No se encontró ningún usuario con el id especificado");
            }

    }

    @Override
    public boolean createUsuario(Usuario usuario) {

        Optional<UsuarioJPAEntity> optionalUsuario = usuarioJPARepository.findById(usuario.getId());
        if (optionalUsuario.isPresent()) {
            return false; // Ya existe un usuario con ese ID
        } else {
            usuarioJPARepository.save(mapperUsuario.UsuarioDomainToUsuarioJPA(usuario));
            return true;
        }

    }

    @Override
    public boolean UpdateUsuario(Usuario usuario) {

        usuarioJPARepository.save(mapperUsuario.UsuarioDomainToUsuarioJPA(usuario));

        // si el usuario está presente en la base de datos significa que se  actualizó correctamente
        return usuarioJPARepository.findById(usuario.getId()).isPresent();

    }

    @Override
    public boolean deleteUsuarioById(Integer id) {

        usuarioJPARepository.deleteById(id);

        return usuarioJPARepository.findById(id).isEmpty();
    }
}
