package com.mycompany.junitperf;

import com.mycompany.controller.MedicoController;
import com.mycompany.DTO.CredencialesMedicoDTO;
import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicoControllerPerfTest {

    private MedicoController controller;
    private MedicoService medicoService;

    @BeforeEach
    void setUp() {
        medicoService = Mockito.mock(MedicoService.class);
        controller = new MedicoController(medicoService);

        Medico medico = new Medico();
        medico.setId(1L);

        Mockito.when(medicoService.buscarPorNombreYApellido("Juan", "Perez")).thenReturn(medico);
        Mockito.when(medicoService.obtenerDisponibilidadMedico(1L))
               .thenReturn(Collections.singletonList(LocalDateTime.now()));
    }

    @SuppressWarnings("deprecation")
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 1000, maxExecutionsPerSecond = 20)
    public void testObtenerDisponibilidadPerformance() {
        CredencialesMedicoDTO dto = new CredencialesMedicoDTO();
        dto.setNombre("Juan");
        dto.setApelliddo("Perez");

        ResponseEntity<?> response = controller.obtenerDisponibilidadMedico(dto);
        assertEquals(200, response.getStatusCodeValue());
    }
}

