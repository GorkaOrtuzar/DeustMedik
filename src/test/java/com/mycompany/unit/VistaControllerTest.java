package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.controller.VistaController;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VistaControllerTest {

    private final VistaController controller = new VistaController();

    @Test
    void testMostrarProximamente() {
        String result = controller.mostrarProximamente();
        assertEquals("proximamente", result);
    }
}

