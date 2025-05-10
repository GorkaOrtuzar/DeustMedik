package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicoTest {

    @Test
    void testDefaultConstructorAndSettersAndGetters() {
        Medico m = new Medico();
        m.setId(7L);
        assertEquals(7L, m.getId());
        m.setDni("DNI-M");
        m.setNombre("Laura");
        m.setApellido("Gómez");
        m.setEspecialidad("Cardio");
        m.setContacto("laura@hosp.com");
        assertEquals("DNI-M", m.getDni());
        assertEquals("Laura", m.getNombre());
        assertEquals("Gómez", m.getApellido());
        assertEquals("Cardio", m.getEspecialidad());
        assertEquals("laura@hosp.com", m.getContacto());
        m.setDisponibilidad(true);
        assertTrue(m.isDisponibilidad());
        m.setDisponibilidad(false);
        assertFalse(m.isDisponibilidad());
        Horario h = new Horario();
        m.setHorarios(List.of(h));
        assertEquals(1, m.getHorarios().size());
        Cita c = new Cita();
        m.setCitas(List.of(c));
        assertEquals(1, m.getCitas().size());
    }

    @Test
    void testAddAndRemoveHorario() {
        Medico m = new Medico();
        Horario h = new Horario();
        m.addHorario(h);
        assertTrue(m.getHorarios().contains(h));
        assertEquals(m, h.getMedico());
        m.removeHorario(h);
        assertFalse(m.getHorarios().contains(h));
        assertNull(h.getMedico());
    }

    @Test
    void testParameterizedConstructor() {
        Horario h1 = new Horario();
        Medico m = new Medico("DNI", "N", "A", "Esp", "Cont", List.of(h1));
        assertEquals("DNI", m.getDni());
        assertEquals("N", m.getNombre());
        assertTrue(m.getHorarios().contains(h1));
    }
}
