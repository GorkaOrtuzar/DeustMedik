package com.mycompany.junitperf;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mycompany.controller.VistaController;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class VistaControllerPerfTest {

    @BeforeAll
    static void createReportDir() {
        new File("target/reports").mkdirs();
    }

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG =
        JUnitPerfReportingConfig.builder()
            .reportGenerator(new CsvReportGenerator("target/reports/vista.csv"))
            .reportGenerator(new HtmlReportGenerator("target/reports/vista.html"))
            .build();

    private VistaController controller;

    @BeforeEach
    void setUp() {
        controller = new VistaController();
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:100",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testMostrarProximamentePerformance() {
        String view = controller.mostrarProximamente();
        assertEquals("proximamente", view);
    }

    @Test
    @JUnitPerfTest(threads = 8, durationMs = 8_000, warmUpMs = 1_500)
    @JUnitPerfTestRequirement(
        percentiles = "90:30,99:80",
        executionsPerSec = 30,
        allowedErrorPercentage = 0
    )
    void testMostrarProximamenteHighLoadPerformance() {
        String view = controller.mostrarProximamente();
        assertEquals("proximamente", view);
    }

    @Test
    @JUnitPerfTest(threads = 3, durationMs = 3_000)
    void testMostrarProximamenteBasicPerformance() {
        String view = controller.mostrarProximamente();
        assertEquals("proximamente", view);
    }

    @Test
    @JUnitPerfTest(threads = 6, durationMs = 6_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:40,99:90",
        executionsPerSec = 25,
        allowedErrorPercentage = 0
    )
    void testMostrarProximamenteMediumLoadPerformance() {
        String view = controller.mostrarProximamente();
        assertEquals("proximamente", view);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 10_000, warmUpMs = 2_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:60,99:150",
        executionsPerSec = 40,
        allowedErrorPercentage = 0
    )
    void testMostrarProximamenteVeryHighLoadPerformance() {
        String view = controller.mostrarProximamente();
        assertEquals("proximamente", view);
    }
}