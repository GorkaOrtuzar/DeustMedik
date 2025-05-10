package com.mycompany.unit;

import com.mycompany.DTO.CitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.HistorialService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistorialServiceTest {

    @Mock
    private RepositorioCita repositorioCita;
    @Mock
    private RepositorioPaciente repositorioPaciente;
    @InjectMocks
    private HistorialService service;

    @Test
    void obtenerCitasPorDni_conMedicoYsinMedico_mapeaCorrectamente() {
        Cita cita1 = new Cita();
        LocalDateTime fecha1 = LocalDateTime.of(2025, 5, 10, 9, 0);
        cita1.setFechaHora(fecha1);
        cita1.setMotivo("Chequeo");
        Medico m = new Medico();
        m.setNombre("Ana");
        m.setApellido("Pérez");
        m.setEspecialidad("Dermatología");
        cita1.setMedico(m);

        Cita cita2 = new Cita();
        LocalDateTime fecha2 = LocalDateTime.of(2025, 5, 11, 10, 30);
        cita2.setFechaHora(fecha2);
        cita2.setMotivo("Urgencia");
        cita2.setMedico(null);

        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("X"))
            .thenReturn(List.of(cita1, cita2));

        List<CitaDTO> dtos = service.obtenerCitasPorDni("X");

        assertEquals(2, dtos.size());
        CitaDTO dto1 = dtos.get(0);
        assertEquals(fecha1, dto1.getFecha());
        assertEquals("Ana", dto1.getMedicoNombre());
        assertEquals("Pérez", dto1.getMedicoApellido());
        assertEquals("Dermatología", dto1.getEspecialidad());
        assertEquals("Chequeo", dto1.getMotivo());
        CitaDTO dto2 = dtos.get(1);
        assertEquals(fecha2, dto2.getFecha());
        assertEquals("undefined", dto2.getMedicoNombre());
        assertEquals("undefined", dto2.getMedicoApellido());
        assertEquals("undefined", dto2.getEspecialidad());
        assertEquals("Urgencia", dto2.getMotivo());

        verify(repositorioCita).findByPacienteDNIOrderByFechaHoraDesc("X");
    }

    @Test
    void obtenerCitasPorDni_listaVacia_devuelveVacio() {
        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("Y"))
            .thenReturn(List.of());

        List<CitaDTO> dtos = service.obtenerCitasPorDni("Y");
        assertTrue(dtos.isEmpty());

        verify(repositorioCita).findByPacienteDNIOrderByFechaHoraDesc("Y");
    }
}
