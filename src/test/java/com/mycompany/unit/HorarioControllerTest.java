package com.mycompany.unit;

import com.mycompany.controller.HorarioController;
import com.mycompany.modelo.Horario;
import com.mycompany.service.HorarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HorarioControllerTest {

    @Mock
    private HorarioService horarioService;

    @InjectMocks
    private HorarioController controller;

    @SuppressWarnings("deprecation")
    @Test
    void testObtenerHorariosPorMedico() {
        List<Horario> horarios = List.of(new Horario());
        when(horarioService.obtenerHorariosPorMedico(1L)).thenReturn(horarios);

        ResponseEntity<List<Horario>> response = controller.obtenerHorariosPorMedico(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(horarios, response.getBody());
    }

    @Test
    void testMostrarHorarios() {
        String result = controller.mostrarHorarios();
        assertEquals("horarios", result);
    }
}
