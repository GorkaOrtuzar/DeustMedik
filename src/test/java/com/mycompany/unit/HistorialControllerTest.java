package com.mycompany.unit;

import com.mycompany.controller.HistorialController;
import com.mycompany.DTO.CitaDTO;
import com.mycompany.service.HistorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistorialControllerTest {

    @Mock
    private HistorialService historialService;

    @InjectMocks
    private HistorialController historialController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerHistorialPorDNI_retornaListaDeCitasDTO() {
        String dni = "12345678A";
        CitaDTO cita1 = mock(CitaDTO.class);
        CitaDTO cita2 = mock(CitaDTO.class);

        when(historialService.obtenerCitasPorDni(dni)).thenReturn(Arrays.asList(cita1, cita2));

        ResponseEntity<List<CitaDTO>> respuesta = historialController.obtenerHistorialPorDNI(dni);

        assertNotNull(respuesta);
        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(2, respuesta.getBody().size());
        verify(historialService, times(1)).obtenerCitasPorDni(dni);
    }
}
