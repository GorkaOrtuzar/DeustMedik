package com.mycompany.unit;

import com.mycompany.controller.CitaController;
import com.mycompany.DTO.ModificarCitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.service.NotificacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CitaControllerTest {

    @Mock
    private RepositorioCita repositorioCita;

    @Mock
    private NotificacionService notiService;

    @InjectMocks
    private CitaController citaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodasLasCitas_retornaListaDeCitas() {
        Cita cita1 = new Cita();
        Cita cita2 = new Cita();
        when(repositorioCita.findAll()).thenReturn(Arrays.asList(cita1, cita2));

        List<Cita> resultado = citaController.obtenerTodasLasCitas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repositorioCita, times(1)).findAll();
    }

    @Test
    void crearCita_guardaYCreaCita() {
        Cita cita = new Cita();
        when(repositorioCita.save(cita)).thenReturn(cita);
        doNothing().when(notiService).crearNotificacion(anyString(), anyString(), anyString());

        ResponseEntity<Cita> respuesta = citaController.crearCita(cita);

        assertNotNull(respuesta);
        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals(cita, respuesta.getBody());
        verify(repositorioCita, times(1)).save(cita);
    }

    @Test
    void actualizarCita_actualizaCitaExistente() {
        ModificarCitaDTO dto = new ModificarCitaDTO();
        dto.setId(1L);
        dto.setFechaHora(LocalDateTime.now());
        dto.setMotivo("Motivo actualizado");

        Cita citaExistente = new Cita();
        when(repositorioCita.findById(1L)).thenReturn(Optional.of(citaExistente));
        doNothing().when(notiService).crearNotificacion(anyString(), anyString(), anyString());

        ResponseEntity<?> respuesta = citaController.actualizarCita(dto);

        assertEquals(200, respuesta.getStatusCode().value());
        verify(repositorioCita, times(1)).findById(1L);
        verify(repositorioCita, times(1)).save(citaExistente);
        assertEquals(dto.getFechaHora(), citaExistente.getFechaHora());
        assertEquals(dto.getMotivo(), citaExistente.getMotivo());
    }

    @Test
    void actualizarCita_citaNoEncontrada() {
        ModificarCitaDTO dto = new ModificarCitaDTO();
        dto.setId(1L);

        when(repositorioCita.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = citaController.actualizarCita(dto);

        assertEquals(404, respuesta.getStatusCode().value());
        verify(repositorioCita, times(1)).findById(1L);
        verify(repositorioCita, never()).save(any(Cita.class));
    }
}
