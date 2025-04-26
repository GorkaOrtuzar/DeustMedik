package com.mycompany.unit;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.HistorialService;
import com.mycompany.DTO.CitaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistorialServiceTest {

    @Mock
    private RepositorioCita repositorioCita;

    @Mock
    private RepositorioPaciente repositorioPaciente;

    @InjectMocks
    private HistorialService historialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerCitasPorDni() {
        Medico medico = new Medico();
        medico.setNombre("Juan");
        medico.setApellido("Pérez");
        medico.setEspecialidad("Cardiología");

        Cita cita = new Cita();
        cita.setFechaHora(LocalDateTime.of(2025, 4, 26, 10, 0));
        cita.setMotivo("Consulta general");
        cita.setMedico(medico);

        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("12345678A"))
            .thenReturn(List.of(cita));

        List<CitaDTO> resultado = historialService.obtenerCitasPorDni("12345678A");

        assertEquals(1, resultado.size());
        CitaDTO citaDTO = resultado.get(0);
        assertEquals("Juan", citaDTO.getMedicoNombre());
        assertEquals("Pérez", citaDTO.getMedicoApellido());
        assertEquals("Cardiología", citaDTO.getEspecialidad());
        assertEquals("Consulta general", citaDTO.getMotivo());
        assertEquals(LocalDateTime.of(2025, 4, 26, 10, 0), citaDTO.getFecha());
    }

    @Test
    void testObtenerCitasPorDni_SinCitas() {
        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("12345678A"))
            .thenReturn(Collections.emptyList());

        List<CitaDTO> resultado = historialService.obtenerCitasPorDni("12345678A");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
