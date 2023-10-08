package com.ProyectoIntegrador.sistematransaccionesbancarias.service;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.RolService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Rol;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RolesServiceTest {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRoles() {
        // Preparación de los datos de prueba
        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(1, "Rol1"));
        roles.add(new Rol(2, "Rol2"));

        // Configuración del comportamiento del mock
        when(rolRepository.getAllRoles()).thenReturn(roles);

        // Ejecución del método a probar
        List<Rol> result = rolService.getAllRoles();

        // Verificación del resultado esperado
        assertEquals(roles.size(), result.size());
        assertEquals(roles.get(0), result.get(0));
        assertEquals(roles.get(1), result.get(1));

        // Verificación de las llamadas a los métodos del mock
        verify(rolRepository, times(1)).getAllRoles();
    }
}
