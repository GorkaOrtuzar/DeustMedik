package com.mycompany.junitperf;

import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.CsvReportGenerator;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import com.mycompany.service.PacienteService;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.modelo.Paciente;
import com.mycompany.modelo.Cita;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(JUnitPerfInterceptor.class)
public class PacienteServicePerfTest {

    @BeforeAll
    static void createReportDir() {
        new File("target/reports").mkdirs();
    }

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG =
        JUnitPerfReportingConfig.builder()
            .reportGenerator(new CsvReportGenerator("target/reports/pacienteService.csv"))
            .reportGenerator(new HtmlReportGenerator("target/reports/pacienteService.html"))
            .build();

    private RepositorioPaciente repoPaciente;
    private RepositorioCita repoCita;
    private PacienteService service;

    @BeforeEach
    void setUp() {
        repoPaciente = Mockito.mock(RepositorioPaciente.class);
        repoCita     = Mockito.mock(RepositorioCita.class);
        service      = new PacienteService(repoPaciente, repoCita);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:100",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testLoginFailurePerformance() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        paciente.setContrasenia("pass");
        Mockito.when(repoPaciente.findByDni("1234"))
               .thenReturn(Optional.of(paciente));

        Optional<String> result = service.login("1234", "wrong");
        assertFalse(result.isPresent());
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:10,99:50",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testGetPacienteByDNINotFoundPerformance() {
        Mockito.when(repoPaciente.findByDni("1234"))
               .thenReturn(Optional.empty());

        Paciente result = service.getPacienteByDNI("1234");
        assertNull(result);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:20,99:80",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testObtenerHistorialCitasExistingPatientPerformance() {
        Paciente paciente = new Paciente();
        paciente.setDni("1234");
        Mockito.when(repoPaciente.findByDni("1234"))
               .thenReturn(Optional.of(paciente));
        Mockito.when(repoCita.findByPacienteDNIOrderByFechaHoraDesc("1234"))
               .thenReturn(Collections.emptyList());

        List<Cita> result = service.obtenerHistorialCitas("1234");
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:20,99:80",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testObtenerHistorialCitasPatientNotFoundPerformance() {
        Mockito.when(repoPaciente.findByDni("1234"))
               .thenReturn(Optional.empty());

        List<Cita> result = service.obtenerHistorialCitas("1234");
        assertTrue(result.isEmpty());
    }
}
