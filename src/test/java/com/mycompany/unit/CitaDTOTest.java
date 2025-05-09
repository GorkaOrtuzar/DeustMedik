package com.mycompany.unit;
import com.mycompany.DTO.CitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitaDTOTest {

    @Test
    void whenMedicoIsNotNull_thenPopulatesAllFields() {
        LocalDateTime fecha = LocalDateTime.of(2023, 10, 5, 14, 30);
        Cita cita = mock(Cita.class);
        Medico medico = mock(Medico.class);

        when(cita.getFechaHora()).thenReturn(fecha);
        when(cita.getMotivo()).thenReturn("Consulta general");
        when(cita.getMedico()).thenReturn(medico);
        when(medico.getNombre()).thenReturn("Juan");
        when(medico.getApellido()).thenReturn("Pérez");
        when(medico.getEspecialidad()).thenReturn("Cardiología");

        CitaDTO dto = new CitaDTO(cita);

        assertEquals(fecha, dto.getFecha());
        assertEquals("Consulta general", dto.getMotivo());
        assertEquals("Juan", dto.getMedicoNombre());
        assertEquals("Pérez", dto.getMedicoApellido());
        assertEquals("Cardiología", dto.getEspecialidad());
    }

    @Test
    void whenMedicoIsNull_thenUsesDefaultValues() {
        LocalDateTime fecha = LocalDateTime.now();
        Cita cita = mock(Cita.class);

        when(cita.getFechaHora()).thenReturn(fecha);
        when(cita.getMotivo()).thenReturn("Revisión");
        when(cita.getMedico()).thenReturn(null);

        CitaDTO dto = new CitaDTO(cita);

        assertEquals(fecha, dto.getFecha());
        assertEquals("Revisión", dto.getMotivo());
        assertEquals("Desconocido", dto.getMedicoNombre());
        assertEquals("", dto.getMedicoApellido());
        assertEquals("Desconocida", dto.getEspecialidad());
    }

    @Test
    void testAlternateConstructorAndGetters() {
        LocalDateTime fecha = LocalDateTime.of(2024, 1, 1, 9, 0);
        CitaDTO dto = new CitaDTO(fecha, "Ana", "Gómez", "Dermatología", "Control piel");

        assertEquals(fecha, dto.getFecha());
        assertEquals("Ana", dto.getMedicoNombre());
        assertEquals("Gómez", dto.getMedicoApellido());
        assertEquals("Dermatología", dto.getEspecialidad());
        assertEquals("Control piel", dto.getMotivo());
    }
}
