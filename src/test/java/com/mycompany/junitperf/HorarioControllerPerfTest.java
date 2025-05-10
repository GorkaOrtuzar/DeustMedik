package com.mycompany.junitperf;

import com.mycompany.controller.HorarioController;
import com.mycompany.service.HorarioService;
import com.mycompany.modelo.Horario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HorarioControllerPerfTest {

    private HorarioController controller;
    private HorarioService horarioService;

    @BeforeEach
    void setUp() {
        horarioService = Mockito.mock(HorarioService.class);
        controller = new HorarioController(horarioService);

        Mockito.when(horarioService.obtenerHorariosPorMedico(1L))
               .thenReturn(Collections.singletonList(new Horario()));
    }

    @SuppressWarnings("deprecation")
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000, maxExecutionsPerSecond = 20)
    public void testObtenerHorariosPorMedicoPerformance() {
        ResponseEntity<?> response = controller.obtenerHorariosPorMedico(1L);
        assertEquals(200, response.getStatusCodeValue());
    }
}
