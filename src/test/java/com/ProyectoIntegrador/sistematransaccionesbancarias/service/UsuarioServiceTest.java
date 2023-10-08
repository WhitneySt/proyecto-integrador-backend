package com.ProyectoIntegrador.sistematransaccionesbancarias.service;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.UsuarioService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Usuario;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.UsuarioRepository;
import com.ProyectoIntegrador.sistematransaccionesbancarias.infraestructure.exepciones.UserNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;


import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ? Test de la clase UsuarioService
@TestMethodOrder(MethodOrderer.class)
public class UsuarioServiceTest {

    @Mock // Mock de la dependencia UsuarioRepository
    private UsuarioRepository usuarioRepository;

    @InjectMocks // Se inyecta el mock de UsuarioRepository al servicio a probar
    private UsuarioService usuarioService;

    List<Usuario> usuarios;
    Date fechaCreacion;


    @BeforeEach // Se ejecuta antes de cada test
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Se inicializan los mocks
         usuarios = new ArrayList<>();
         fechaCreacion = new Date(2021, 1, 1);
    }

    @Test
    @Order(1)
    public void testGetAllUsuarios() {

        // Preparación de los datos de prueba

        Date fechaCreacion = new Date(2021, 1, 1);

        usuarios.add(new Usuario(1, "Usuario1","correo1","contrasena1",fechaCreacion,"urlImage1"));
        usuarios.add(new Usuario(2, "Usuario2","correo2","contrasena2",fechaCreacion,"urlImage2"));

        // Configuración del comportamiento del mock
        when(usuarioRepository.getAllUsuarios()).thenReturn(usuarios);// Se configura el mock para que retorne la lista de usuarios cuando se llame al método getAllUsuarios()


        // Ejecución del método a probar
        List<Usuario> result = usuarioService.getAllUsuarios();

        // Verificación del resultado esperado
        assertEquals(usuarios.size(), result.size()); // Se verifica que el tamaño de la lista sea el mismo
        assertEquals(usuarios.get(0), result.get(0)); // Se verifica que los elementos de la lista sean los mismos
        assertEquals(usuarios.get(1), result.get(1));

        // Verificación de las llamadas a los métodos del mock
        verify(usuarioRepository, times(1)).getAllUsuarios(); // Se verifica que el método getAllUsuarios() del mock se haya llamado exactamente 1 vez
    }

    @Test
    @Order(2)
    public void testGetUsuarioById_UsuarioExistente() {

        // Preparación de los datos de prueba
        int userId = 1;
        Usuario usuario=new Usuario(userId, "Usuario1","correo1","contrasena1",fechaCreacion,"urlImage1");

        // Configuración del comportamiento del mock
        when(usuarioRepository.getUsuarioById(userId)).thenReturn(usuario);

        // Ejecución del método a probar
        Usuario result = usuarioService.getUsuarioById(userId);

        // Verificación del resultado esperado
        assertEquals(usuario, result);

        // Verificación de las llamadas a los métodos del mock
        verify(usuarioRepository, times(1)).getUsuarioById(userId);
    }

    @Test
    @Order(3)
    public void testGetUsuarioById_UsuarioNoExistente() {

        int userId = 1;

        Usuario usuario = usuarioService.getUsuarioById(userId);

        assertNull(usuario); // Se verifica que el usuario sea nulo

    }

    @Test
    @Order(4)
    public void testCreateUsuario_UsuarioNoExistente() {
        // Preparación de los datos de prueba
        Usuario usuario =new Usuario(2, "Usuario1","correo1","contrasena1",fechaCreacion,"urlImage1");

        // Configuración del comportamiento del mock
        when(usuarioRepository.getUsuarioById(usuario.getId())).thenThrow(UserNotFoundException.class);

        // Ejecución del método a probar
        boolean result = usuarioService.createUsuario(usuario);

        // Verificación del resultado esperado
        assertTrue(result);

        // Verificación de las llamadas a los métodos del mock
        verify(usuarioRepository, times(1)).getUsuarioById(usuario.getId());
        verify(usuarioRepository, times(1)).createUsuario(usuario);
    }

    @Test
    @Order(5)
    public void testCreateUsuario_UsuarioExistente() {
        // Preparación de los datos de prueba
        Usuario usuario =new Usuario(2, "Usuario1","correo1","contrasena1",fechaCreacion,"urlImage1");

        // Configuración del comportamiento del mock
        when(usuarioRepository.getUsuarioById(usuario.getId())).thenReturn(usuario);

        // Ejecución del método a probar
        boolean result = usuarioService.createUsuario(usuario);

        // Verificación del resultado esperado
        assertFalse(result);

        // Verificación de las llamadas a los métodos del mock
        verify(usuarioRepository, times(1)).getUsuarioById(usuario.getId());
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    @Order(6)
    public void testUpdateUsuario() {
        // Preparación de los datos de prueba
        Usuario usuario = new Usuario(2, "Usuario1","correo1","contrasena1",fechaCreacion,"urlImage1");

        // Configuración del comportamiento del mock
        when(usuarioRepository.getUsuarioById(usuario.getId())).thenReturn(usuario);

        // Ejecución del método a probar
        boolean result = usuarioService.UpdateUsuario(usuario);

        // Verificación del resultado esperado
        assertTrue(result);

        // Verificación de las llamadas a los métodos del mock
        verify(usuarioRepository, times(1)).getUsuarioById(usuario.getId());
        verify(usuarioRepository, times(1)).UpdateUsuario(usuario);
    }

    @Test
    @Order(7)
    public void testDeleteUsuarioById() {
        // Preparación de los datos de prueba
        int userId = 1;

        // Configuración del comportamiento del mock
        when(usuarioRepository.getUsuarioById(userId)).thenReturn(null);

        // Ejecución del método a probar
        boolean result = usuarioService.deleteUsuarioById(userId);

        // Verificación del resultado esperado
        assertTrue(result);

        // Verificación de las llamadas a los métodos del mock
        verify(usuarioRepository, times(1)).getUsuarioById(userId);
        verify(usuarioRepository, times(1)).deleteUsuarioById(userId);
    }


}
