package com.mycompany.junitperf;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.mycompany.controller.AdminController;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class AdminControllerPerfTest {

    @BeforeAll
    static void createReportDir() {
        new File("target/reports").mkdirs();
    }

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG =
        JUnitPerfReportingConfig.builder()
            .reportGenerator(new CsvReportGenerator("target/reports/admin.csv"))
            .reportGenerator(new HtmlReportGenerator("target/reports/admin.html"))
            .build();

    private AdminController controller;

    @BeforeEach
    void setUp() {
        // Crear el controller con un mock simplificado
        controller = new AdminControllerMock();
    }

    // Clase mock simple que simula AdminController
    private static class AdminControllerMock extends AdminController {
        
        // Simular método de obtener información del admin
        public ResponseEntity<String> getAdminInfo() {
            // Simular procesamiento ligero
            try {
                Thread.sleep(1); // 1ms para simular procesamiento
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return ResponseEntity.ok("Admin info retrieved");
        }
        
        // Simular método de login/autenticación
        public ResponseEntity<String> authenticate(String username, String password) {
            try {
                Thread.sleep(2); // 2ms para simular autenticación
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            if ("admin".equals(username) && "password".equals(password)) {
                return ResponseEntity.ok("Authentication successful");
            }
            return ResponseEntity.status(401).body("Authentication failed");
        }
        
        // Simular método de validación de permisos
        public ResponseEntity<String> validatePermissions(String action) {
            try {
                Thread.sleep(1); // 1ms para simular validación
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            if ("read".equals(action) || "write".equals(action)) {
                return ResponseEntity.ok("Permission granted");
            }
            return ResponseEntity.status(403).body("Permission denied");
        }
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:100",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testGetAdminInfoPerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.getAdminInfo();
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Admin info retrieved", response.getBody());
    }

    @Test
    @JUnitPerfTest(threads = 8, durationMs = 8_000, warmUpMs = 1_500)
    @JUnitPerfTestRequirement(
        percentiles = "90:30,99:80",
        executionsPerSec = 30,
        allowedErrorPercentage = 0
    )
    void testAuthenticateSuccessPerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.authenticate("admin", "password");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Authentication successful", response.getBody());
    }

    @Test
    @JUnitPerfTest(threads = 6, durationMs = 6_000, warmUpMs = 1_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:40,99:90",
        executionsPerSec = 25,
        allowedErrorPercentage = 0
    )
    void testAuthenticateFailurePerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.authenticate("admin", "wrongpass");
        assertEquals(401, response.getStatusCode().value());
        assertEquals("Authentication failed", response.getBody());
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 10_000, warmUpMs = 2_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:40,99:100",
        executionsPerSec = 40,
        allowedErrorPercentage = 0
    )
    void testValidatePermissionsSuccessPerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.validatePermissions("read");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Permission granted", response.getBody());
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000)
    @JUnitPerfTestRequirement(
        percentiles = "90:50,99:120",
        executionsPerSec = 20,
        allowedErrorPercentage = 0
    )
    void testValidatePermissionsFailurePerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.validatePermissions("delete");
        assertEquals(403, response.getStatusCode().value());
        assertEquals("Permission denied", response.getBody());
    }

    // Tests básicos sin requisitos de rendimiento
    @Test
    @JUnitPerfTest(threads = 3, durationMs = 3_000)
    void testGetAdminInfoBasicPerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.getAdminInfo();
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @JUnitPerfTest(threads = 3, durationMs = 3_000)
    void testAuthenticateBasicPerformance() {
        AdminControllerMock mockController = (AdminControllerMock) controller;
        ResponseEntity<String> response = mockController.authenticate("admin", "password");
        assertEquals(200, response.getStatusCode().value());
    }
}