package com.mycompany.junitperf;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import com.mycompany.controller.EstadisticasController;
import com.mycompany.repositorio.RepositorioEstadisticas;
import com.mycompany.DTO.CitasPorMesDTO;
import com.mycompany.DTO.MedicoSolicitadoDTO;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class EstadisticasControllerPerfTest {

  @BeforeAll
  static void createReportDir() {
    new File("target/reports").mkdirs();
  }

  @JUnitPerfTestActiveConfig
  private static final JUnitPerfReportingConfig PERF_CONFIG =
    JUnitPerfReportingConfig.builder()
      .reportGenerator(new CsvReportGenerator("target/reports/estadisticas.csv"))
      .reportGenerator(new HtmlReportGenerator("target/reports/estadisticas.html"))
      .build();

  private EstadisticasController controller;
  private RepositorioEstadisticas repo;

  @BeforeEach
  void setUp() {
    repo = Mockito.mock(RepositorioEstadisticas.class);
    Mockito.when(repo.countCitasByMes())
           .thenReturn(Collections.<CitasPorMesDTO>emptyList());
    Mockito.when(repo.countMedicosMasSolicitados())
           .thenReturn(Collections.<MedicoSolicitadoDTO>emptyList());
    controller = new EstadisticasController(repo);
  }

  @Test
  @JUnitPerfTest(threads = 5, durationMs = 5_000)
  void testCitasPorMesPerformance() {
    List<CitasPorMesDTO> list = controller.citasPorMes();
    assertEquals(0, list.size());
  }

  @Test
  @JUnitPerfTest(threads = 5, durationMs = 5_000)
  void testMedicosMasSolicitadosPerformance() {
    List<MedicoSolicitadoDTO> list = controller.medicosMasSolicitados();
    assertEquals(0, list.size());
  }
}

