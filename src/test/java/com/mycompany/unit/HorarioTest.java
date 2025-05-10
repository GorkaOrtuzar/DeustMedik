package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioTest {

    @Test
    void testDefaultConstructorAndSettersAndGetters() {
        Horario h = new Horario();
        assertNull(h.getId());
        h.setDia("Lunes");
        assertEquals("Lunes", h.getDia());
        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(17, 0);
        h.setHoraInicio(inicio);
        h.setHoraFin(fin);
        assertEquals(inicio, h.getHoraInicio());
        assertEquals(fin, h.getHoraFin());
        Medico m = new Medico();
        h.setMedico(m);
        assertEquals(m, h.getMedico());
    }

    @Test
    void testParameterizedConstructor() {
        Medico m = new Medico();
        LocalTime i = LocalTime.of(8, 30);
        LocalTime f = LocalTime.of(12, 30);
        Horario h = new Horario("Martes", i, f, m);
        assertEquals("Martes", h.getDia());
        assertEquals(i, h.getHoraInicio());
        assertEquals(f, h.getHoraFin());
        assertEquals(m, h.getMedico());
    }
}
