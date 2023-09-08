package com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.jpaRepositories.usuario;

import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.UsuarioRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.data.dbo.UsuarioJPAEntity;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.mapper.MapperClass;

import java.util.ArrayList;
import java.util.List;

public class UsuarioImplementacion implements UsuarioRepository {

    // ?  Inyección de dependencias
    private  final UsuarioJPARepository usuarioJPARepository;
    private MapperClass mapperClass;

    public UsuarioImplementacion(UsuarioJPARepository usuarioJPARepository, MapperClass mapperClass) {
        this.usuarioJPARepository = usuarioJPARepository;
        this.mapperClass = mapperClass;

    }

    @Override
    public List<Usuario> getAllUsuarios() {

        List<Usuario> usuariosList = new ArrayList<>();
        // obtener todos los usuarios de la base de datos y los  guardar en la lista usuarios, se tiene que hacer el mapeo de la entidad de la base de datos a la entidad de dominio
        usuarioJPARepository.findAll().forEach(Usuario -> usuariosList.add(mapperClass.UsuarioJPAToUsuarioDomain(Usuario)));

        return usuariosList;
    }

    @Override
    public Usuario getUsuarioById(Integer id) {

        // obtengo el usuario de la base de datos
        UsuarioJPAEntity usuarioJPAEntity = usuarioJPARepository.findById(id).get();
        return mapperClass.UsuarioJPAToUsuarioDomain(usuarioJPAEntity);  // se tiene que hacer el mapeo de la entidad de la base de datos a la entidad de dominio para que se retorne el tipo de valor correcto

    }

    @Override
    public boolean saveOrUpdateUsuario(Usuario usuario) {

        usuarioJPARepository.save(mapperClass.UsuarioDomainToUsuarioJPA(usuario));

        // si el usuario está presente en la base de datos significa que se guardó o actualizó correctamente
        return usuarioJPARepository.findById(usuario.getId()).isPresent();

    }

    @Override
    public boolean deleteUsuarioById(Integer id) {

        usuarioJPARepository.deleteById(id);

        return usuarioJPARepository.findById(id).isEmpty();
    }
}
