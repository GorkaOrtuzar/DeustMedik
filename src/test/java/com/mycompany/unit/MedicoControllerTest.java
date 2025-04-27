package com.mycompany.unit;

import com.mycompany.controller.MedicoController;
import com.mycompany.DTO.CredencialesMedicoDTO;
import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicoControllerTest {

    @Mock
    private MedicoService servicioMedico;

    @InjectMocks
    private MedicoController medicoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos_conMedicos() {
        Medico medico1 = new Medico();
        Medico medico2 = new Medico();

        when(servicioMedico.obtenerMedicos()).thenReturn(Arrays.asList(medico1, medico2));

        ResponseEntity<List<Medico>> respuesta = medicoController.obtenerTodos();

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(2, respuesta.getBody().size());
        verify(servicioMedico, times(1)).obtenerMedicos();
    }

    @Test
    void obtenerTodos_sinMedicos() {
        when(servicioMedico.obtenerMedicos()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Medico>> respuesta = medicoController.obtenerTodos();

        assertEquals(204, respuesta.getStatusCode().value());
        verify(servicioMedico, times(1)).obtenerMedicos();
    }

    @Test
    void obtenerDisponibilidadMedico_conDisponibilidad() {
        CredencialesMedicoDTO credenciales = new CredencialesMedicoDTO();
        credenciales.setNombre("Juan");
        credenciales.setApelliddo("Perez");

        Medico medico = new Medico();
        medico.setId(1L);

        when(servicioMedico.buscarPorNombreYApellido("Juan", "Perez")).thenReturn(medico);
        when(servicioMedico.obtenerDisponibilidadMedico(1L)).thenReturn(Arrays.asList(LocalDateTime.now()));

        ResponseEntity<List<LocalDateTime>> respuesta = medicoController.obtenerDisponibilidadMedico(credenciales);

        assertEquals(200, respuesta.getStatusCode().value());
        assertFalse(respuesta.getBody().isEmpty());
        verify(servicioMedico, times(1)).buscarPorNombreYApellido("Juan", "Perez");
        verify(servicioMedico, times(1)).obtenerDisponibilidadMedico(1L);
    }

    @Test
    void obtenerDisponibilidadMedico_sinDisponibilidad() {
        CredencialesMedicoDTO credenciales = new CredencialesMedicoDTO();
        credenciales.setNombre("Juan");
        credenciales.setApelliddo("Perez");

        Medico medico = new Medico();
        medico.setId(1L);

        when(servicioMedico.buscarPorNombreYApellido("Juan", "Perez")).thenReturn(medico);
        when(servicioMedico.obtenerDisponibilidadMedico(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<LocalDateTime>> respuesta = medicoController.obtenerDisponibilidadMedico(credenciales);

        assertEquals(204, respuesta.getStatusCode().value());
        verify(servicioMedico, times(1)).buscarPorNombreYApellido("Juan", "Perez");
        verify(servicioMedico, times(1)).obtenerDisponibilidadMedico(1L);
    }

    @Test
    void obtenerHorarioPorNombreYApellido_medicoEncontrado() {
        Medico medico = new Medico();
        medico.setId(2L);

        when(servicioMedico.buscarPorNombreYApellido("Ana", "Gomez")).thenReturn(medico);
        when(servicioMedico.obtenerDisponibilidadMedico(2L)).thenReturn(Arrays.asList(LocalDateTime.now()));

        ResponseEntity<?> respuesta = medicoController.obtenerHorarioPorNombreYApellido("Ana", "Gomez");

        assertEquals(200, respuesta.getStatusCode().value());
        verify(servicioMedico, times(1)).buscarPorNombreYApellido("Ana", "Gomez");
        verify(servicioMedico, times(1)).obtenerDisponibilidadMedico(2L);
    }

    @Test
    void obtenerHorarioPorNombreYApellido_medicoNoEncontrado() {
        when(servicioMedico.buscarPorNombreYApellido("Ana", "Gomez")).thenReturn(null);

        ResponseEntity<?> respuesta = medicoController.obtenerHorarioPorNombreYApellido("Ana", "Gomez");

        assertEquals(404, respuesta.getStatusCode().value());
        verify(servicioMedico, times(1)).buscarPorNombreYApellido("Ana", "Gomez");
    }
}

