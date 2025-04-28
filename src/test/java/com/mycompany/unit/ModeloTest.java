package com.mycompany.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;

class ModeloTest {

    @Test
    void testCita() {
        Medico medico = new Medico("12345678A", "Laura", "Gomez", "Cardiologia", "contacto@example.com", null);
        LocalDateTime fechaHora = LocalDateTime.now();

        Cita cita = new Cita(medico, "12345678Z", "Juan", "Perez", fechaHora, "Consulta general");

        assertEquals("Juan", cita.getPacienteNombre());
        assertEquals("Perez", cita.getPacienteApellido());
        assertEquals("12345678Z", cita.getPacienteDNI());
        assertEquals("Consulta general", cita.getMotivo());
        assertEquals(medico, cita.getMedico());
        assertEquals(fechaHora, cita.getFechaHora());

        cita.setMotivo("Revisión anual");
        assertEquals("Revisión anual", cita.getMotivo());
    }

    @Test
    void testHorario() {
        Medico medico = new Medico("87654321B", "Pedro", "Martinez", "Dermatología", "pedro@example.com", null);
        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(17, 0);

        Horario horario = new Horario("Lunes", inicio, fin, medico);

        assertEquals("Lunes", horario.getDia());
        assertEquals(inicio, horario.getHoraInicio());
        assertEquals(fin, horario.getHoraFin());
        assertEquals(medico, horario.getMedico());

        horario.setDia("Martes");
        assertEquals("Martes", horario.getDia());
    }

    @Test
    void testMedico() {
        List<Horario> horarios = new ArrayList<>();
        Medico medico = new Medico("11223344C", "Ana", "Lopez", "Pediatría", "ana@example.com", horarios);

        assertEquals("Ana", medico.getNombre());
        assertEquals("Lopez", medico.getApellido());
        assertEquals("Pediatría", medico.getEspecialidad());
        assertEquals("ana@example.com", medico.getContacto());
        assertFalse(medico.isDisponibilidad());

        medico.setDisponibilidad(true);
        assertTrue(medico.isDisponibilidad());
    }

    @Test
    void testPaciente() {
        Medico medico = new Medico("99887766D", "Carlos", "Sanchez", "Oftalmología", "carlos@example.com", null);
        Paciente paciente = new Paciente("44556677E", "Luis", "Ramirez", "luis@example.com", "password123", "Historial limpio", medico);

        assertEquals("Luis", paciente.getNombre());
        assertEquals("Ramirez", paciente.getApellido());
        assertEquals("luis@example.com", paciente.getCorreo());
        assertEquals("password123", paciente.getContrasenia());
        assertEquals("Historial limpio", paciente.getHistorial());
        assertEquals(medico, paciente.getMedico());

        paciente.setNombre("Luis Miguel");
        assertEquals("Luis Miguel", paciente.getNombre());
    }
}
