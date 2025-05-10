package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;

import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    void testDefaultConstructorAndSettersAndGetters() {
        Paciente p = new Paciente();
        p.setDni("P-DNI");
        p.setNombre("Pedro");
        p.setApellido("Ruiz");
        p.setCorreo("pedro@ej.com");
        p.setContrasenia("1234");

        assertEquals("P-DNI", p.getDni());
        assertEquals("Pedro", p.getNombre());
        assertEquals("Ruiz", p.getApellido());
        assertEquals("pedro@ej.com", p.getCorreo());
        assertEquals("1234", p.getContrasenia());
    }

    @Test
    void testParameterizedConstructor() {
        Medico m = new Medico();
        Paciente p = new Paciente("DNI-P", "Ana", "Soto", "ana@ej.com", "pass", "Historial completo", m);

        assertEquals("DNI-P", p.getDni());
        assertEquals("Ana", p.getNombre());
        assertEquals("Soto", p.getApellido());
        assertEquals("ana@ej.com", p.getCorreo());
        assertEquals("pass", p.getContrasenia());
        assertEquals("Historial completo", p.getHistorial());
        assertEquals(m, p.getMedico());
    }
}

