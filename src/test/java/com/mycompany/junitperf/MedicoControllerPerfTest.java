package com.mycompany.junitperf;

import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.reporting.providers.CsvReportGenerator;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.mycompany.controller.MedicoController;
import com.mycompany.service.MedicoService;
import com.mycompany.modelo.Medico;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class MedicoControllerPerfTest {

  @BeforeAll
  static void createReportDir() {
    new File("target/reports").mkdirs();
  }

  @JUnitPerfTestActiveConfig
  private static final JUnitPerfReportingConfig PERF_CONFIG =
    JUnitPerfReportingConfig.builder()
      .reportGenerator(new CsvReportGenerator("target/reports/junitperf_report.csv"))
      .reportGenerator(new HtmlReportGenerator("target/reports/junitperf_report.html"))
      .build();

  private MedicoController controller;

  @BeforeEach
  void setUp() {
    MedicoService svc = Mockito.mock(MedicoService.class);
    Mockito.when(svc.obtenerMedicos()).thenReturn(Collections.<Medico>emptyList());
    controller = new MedicoController(svc);
  }

  @Test
  @JUnitPerfTest(
    threads = 5,
    durationMs = 10_000,
    maxExecutionsPerSecond = 100
  )
  @JUnitPerfTestRequirement(
    allowedErrorPercentage = 100.0f
  )
  void testObtenerMedicosPerformance() {
    ResponseEntity<List<Medico>> resp = controller.obtenerTodos();
    assertEquals(200, resp.getStatusCodeValue());
  }
}
