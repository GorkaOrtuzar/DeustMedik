package com.mycompany.junitperf;

import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.reporting.providers.CsvReportGenerator;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.mycompany.controller.RegistroController;
import com.mycompany.service.RegistroService;
import com.mycompany.modelo.Paciente;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JUnitPerfInterceptor.class)
public class RegistroControllerPerfTest {

    @BeforeAll
    static void createReportDir() {
        new File("target/reports").mkdirs();
    }

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG =
        JUnitPerfReportingConfig.builder()
            .reportGenerator(new CsvReportGenerator("target/reports/registroController.csv"))
            .reportGenerator(new HtmlReportGenerator("target/reports/registroController.html"))
            .build();

    private RegistroService registroService;
    private RegistroController controller;

    @BeforeEach
    void setUp() {
        registroService = Mockito.mock(RegistroService.class);
        controller      = new RegistroController(registroService);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:100",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testMostrarFormularioRegistroPerformance() {
        String view = controller.mostrarFormularioRegistro();
        assertEquals("Registro", view);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:100",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testRegistrarPacienteBadRequestPerformance() {
        Paciente paciente = new Paciente();
        Mockito.when(registroService.registrarPaciente(paciente))
               .thenReturn(false);

        ResponseEntity<?> response = controller.registrar(paciente);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El DNI ya está registrado", response.getBody());
    }
}
