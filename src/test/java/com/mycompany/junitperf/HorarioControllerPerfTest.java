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

import com.mycompany.controller.HorarioController;
import com.mycompany.service.HorarioService;
import com.mycompany.modelo.Horario;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class HorarioControllerPerfTest {

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

  private HorarioController controller;

  @BeforeEach
  void setUp() {
    HorarioService svc = Mockito.mock(HorarioService.class);
    Mockito.when(svc.obtenerHorariosPorMedico(1L))
           .thenReturn(Collections.<Horario>emptyList());
    controller = new HorarioController(svc);
  }

  @Test
  @JUnitPerfTest(threads = 5, durationMs = 10_000, maxExecutionsPerSecond = 100)
  @JUnitPerfTestRequirement(meanLatency = 50)
  void testObtenerHorariosPorMedicoPerformance() {
    ResponseEntity<List<Horario>> resp = controller.obtenerHorariosPorMedico(1L);
    assertEquals(200, resp.getStatusCodeValue());
  }
}
