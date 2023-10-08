package com.ProyectoIntegrador.sistematransaccionesbancarias.service;

import com.ProyectoIntegrador.sistematransaccionesbancarias.application.services.EstadoService;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.entities.Estado;
import com.ProyectoIntegrador.sistematransaccionesbancarias.domain.repositories.EstadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EstadoServiceTest {

    @Mock
    private EstadoRepository estadoRepository;

    @InjectMocks
    private EstadoService estadoService;

    List<Estado> estados;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        estados = new ArrayList<>();

    }

    @Test
    public void testGetAllEstados() {

        // Preparación de los datos de prueba
        estados.add(new Estado(1, true));
        estados.add(new Estado(2, false));

        // Configuración del comportamiento del mock
        when(estadoRepository.getAllEstados()).thenReturn(estados);

        // Ejecución del método a probar
        List<Estado> result = estadoService.getAllEstados();

        // Verificación del resultado esperado
        assertEquals(estados.size(), result.size());
        assertEquals(estados.get(0), result.get(0));
        assertEquals(estados.get(1), result.get(1));

        // Verificación de las llamadas a los métodos del mock
        verify(estadoRepository, times(1)).getAllEstados();
    }
}
