package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Mensaje;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MensajeTest {

    @Test
    void testDefaultConstructorAndSettersAndGetters() {
        Mensaje m = new Mensaje();
        assertNull(m.getId());
        m.setRemitenteDni("R1");
        m.setDestinatarioDni("D1");
        assertEquals("R1", m.getRemitenteDni());
        assertEquals("D1", m.getDestinatarioDni());
        m.setContenido("¡Hola!");
        assertEquals("¡Hola!", m.getContenido());
        LocalDateTime f = LocalDateTime.of(2025,1,1,9,0);
        m.setFecha(f);
        assertEquals(f, m.getFecha());
    }
}
