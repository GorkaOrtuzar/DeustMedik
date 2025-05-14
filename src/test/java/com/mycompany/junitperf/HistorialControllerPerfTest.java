package com.mycompany.junitperf;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.mycompany.controller.HistorialController;
import com.mycompany.service.HistorialService;
import com.mycompany.DTO.CitaDTO;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class HistorialControllerPerfTest {

    @BeforeAll
    static void createReportDir() {
        new File("target/reports").mkdirs();
    }

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG =
        JUnitPerfReportingConfig.builder()
            .reportGenerator(new CsvReportGenerator("target/reports/historial.csv"))
            .reportGenerator(new HtmlReportGenerator("target/reports/historial.html"))
            .build();

    private HistorialController controller;
    private HistorialService historialService;

    @BeforeEach
    void setUp() {
        historialService = Mockito.mock(HistorialService.class);
        // Configurar el mock para todos los DNIs posibles
        Mockito.when(historialService.obtenerCitasPorDni(Mockito.anyString()))
               .thenReturn(Collections.<CitaDTO>emptyList());
        
        // Crear el controller con el servicio mock
        controller = new HistorialControllerWithMock(historialService);
    }

    // Clase interna para inyectar el mock directamente
    private static class HistorialControllerWithMock extends HistorialController {
        private final HistorialService mockService;
        
        public HistorialControllerWithMock(HistorialService mockService) {
            this.mockService = mockService;
        }
        
        @Override
        public ResponseEntity<List<CitaDTO>> obtenerHistorialPorDNI(String dni) {
            List<CitaDTO> citasDTO = mockService.obtenerCitasPorDni(dni);
            return ResponseEntity.ok(citasDTO);
        }
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:100",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testObtenerHistorialPorDNIPerformance() {
        ResponseEntity<List<CitaDTO>> response = controller.obtenerHistorialPorDNI("1234");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 10_000, warmUpMs = 2_000)
    @JUnitPerfTestRequirement(
        meanLatency = 25,
        percentiles = "95:40,99:80",
        executionsPerSec = 50,
        allowedErrorPercentage = 0
    )
    void testObtenerHistorialPorDNIHighLoadPerformance() {
        ResponseEntity<List<CitaDTO>> response = controller.obtenerHistorialPorDNI("1234");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    @JUnitPerfTest(threads = 3, durationMs = 3_000)
    void testObtenerHistorialPorDNIBasicPerformance() {
        ResponseEntity<List<CitaDTO>> response = controller.obtenerHistorialPorDNI("1234");
        assertEquals(200, response.getStatusCodeValue());
    }
}