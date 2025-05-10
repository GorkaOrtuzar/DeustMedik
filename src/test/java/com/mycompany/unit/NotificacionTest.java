package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.modelo.Notificacion;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

    @Test
    void testDefaultConstructorAndSettersAndGetters() {
        Notificacion n = new Notificacion();
        n.setId(99L);
        assertEquals(99L, n.getId());
        n.setUsuarioDni("UX");
        assertEquals("UX", n.getUsuarioDni());
        n.setTitulo("Alerta");
        n.setCuerpo("Tienes una cita");
        assertEquals("Alerta", n.getTitulo());
        assertEquals("Tienes una cita", n.getCuerpo());
        LocalDateTime f = LocalDateTime.now().plusDays(1);
        n.setFecha(f);
        assertEquals(f, n.getFecha());
    }
}
