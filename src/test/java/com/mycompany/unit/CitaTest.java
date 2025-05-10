package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CitaTest {

    @Test
    void testDefaultConstructorAndSettersAndGetters() {
        Cita cita = new Cita();
        cita.setId(42L);
        assertEquals(42L, cita.getId());
        Medico medico = new Medico("DNI-M", "Ana", "Pérez", "Dermatología", "ana@ej.com", null);
        cita.setMedico(medico);
        assertEquals(medico, cita.getMedico());
        cita.setPacienteDNI("1234X");
        assertEquals("1234X", cita.getPacienteDNI());
        cita.setPacienteNombre("Juan");
        assertEquals("Juan", cita.getPacienteNombre());
        cita.setPacienteApellido("López");
        assertEquals("López", cita.getPacienteApellido());
        LocalDateTime now = LocalDateTime.now();
        cita.setFechaHora(now);
        assertEquals(now, cita.getFechaHora());
        cita.setMotivo("Chequeo anual");
        assertEquals("Chequeo anual", cita.getMotivo());
    }

    @Test
    void testParameterizedConstructor() {
        Medico m = new Medico("DNI-M", "Ana", "Pérez", "Dermatología", "ana@ej.com", null);
        LocalDateTime fecha = LocalDateTime.of(2025, 5, 10, 14, 30);
        Cita cita = new Cita(m, "9999Z", "María", "García", fecha, "Consulta");
        assertEquals(m, cita.getMedico());
        assertEquals("9999Z", cita.getPacienteDNI());
        assertEquals("María", cita.getPacienteNombre());
        assertEquals("García", cita.getPacienteApellido());
        assertEquals(fecha, cita.getFechaHora());
        assertEquals("Consulta", cita.getMotivo());
    }
}
