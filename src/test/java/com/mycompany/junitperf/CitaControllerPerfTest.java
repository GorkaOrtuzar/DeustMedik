package com.mycompany.junitperf;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import com.mycompany.controller.CitaController;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.modelo.Cita;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class CitaControllerPerfTest {

  @BeforeAll
  static void createReportDir() {
    new File("target/reports").mkdirs();
  }

  @JUnitPerfTestActiveConfig
  private static final JUnitPerfReportingConfig PERF_CONFIG =
    JUnitPerfReportingConfig.builder()
      .reportGenerator(new CsvReportGenerator("target/reports/cita.csv"))
      .reportGenerator(new HtmlReportGenerator("target/reports/cita.html"))
      .build();

  private CitaController controller;
  private RepositorioCita repo;

  @BeforeEach
  void setUp() {
    repo = Mockito.mock(RepositorioCita.class);
    Mockito.when(repo.findAll()).thenReturn(Collections.<Cita>emptyList());
    controller = new CitaController(repo);
  }

  @Test
  @JUnitPerfTest(threads = 5, durationMs = 5_000)
  void testObtenerTodasLasCitasPerformance() {
    List<Cita> citas = controller.obtenerTodasLasCitas();
    assertEquals(0, citas.size());
  }
}

