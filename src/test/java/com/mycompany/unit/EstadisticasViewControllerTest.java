package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.controller.EstadisticasViewController;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EstadisticasViewControllerTest {

    private final EstadisticasViewController controller = new EstadisticasViewController();

    @Test
    void testMostrarEstadisticas() {
        String result = controller.mostrarEstadisticas();
        assertEquals("forward:/estadisticas.html", result);
    }
}

