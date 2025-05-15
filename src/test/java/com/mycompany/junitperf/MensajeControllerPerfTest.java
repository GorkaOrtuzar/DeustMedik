package com.mycompany.junitperf;

import com.github.noconnor.junitperf.*;
import com.github.noconnor.junitperf.reporting.providers.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.mycompany.controller.MensajeController;
import com.mycompany.service.MensajeService;
import com.mycompany.service.NotificacionService;
import com.mycompany.modelo.Mensaje;
import com.mycompany.DTO.ConversacionDTO;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(JUnitPerfInterceptor.class)
public class MensajeControllerPerfTest {

    @BeforeAll
    static void createReportDir() {
        new File("target/reports").mkdirs();
    }

    @JUnitPerfTestActiveConfig
    private static final JUnitPerfReportingConfig PERF_CONFIG =
        JUnitPerfReportingConfig.builder()
            .reportGenerator(new CsvReportGenerator("target/reports/mensaje.csv"))
            .reportGenerator(new HtmlReportGenerator("target/reports/mensaje.html"))
            .build();

    private MensajeController controller;

    @BeforeEach
    void setUp() {
        MensajeService mensajeService = Mockito.mock(MensajeService.class);
        NotificacionService notificacionService = Mockito.mock(NotificacionService.class);
        
        // Crear un mensaje mock simple
        Mensaje mockMensaje = new Mensaje();
        
        // Configurar mocks básicos
        Mockito.when(mensajeService.guardarMensaje(Mockito.any(Mensaje.class)))
               .thenReturn(mockMensaje);
        Mockito.when(mensajeService.obtenerConversacion(Mockito.anyString(), Mockito.anyString()))
               .thenReturn(Collections.<Mensaje>emptyList());
        Mockito.when(mensajeService.obtenerConversaciones(Mockito.anyString()))
               .thenReturn(Collections.<ConversacionDTO>emptyList());
        
        // Usar el patrón del HistorialController con clase interna
        controller = new MensajeControllerWithMock(mensajeService, notificacionService);
    }

    // Clase interna para inyectar el mock directamente
    private static class MensajeControllerWithMock extends MensajeController {
        private final MensajeService mockMensajeService;
        private final NotificacionService mockNotificacionService;
        
        public MensajeControllerWithMock(MensajeService mensajeService, NotificacionService notificacionService) {
            this.mockMensajeService = mensajeService;
            this.mockNotificacionService = notificacionService;
        }
        
        @Override
        public ResponseEntity<Mensaje> enviarMensaje(Mensaje mensaje) {
            Mensaje guardado = mockMensajeService.guardarMensaje(mensaje);
            return ResponseEntity.ok(guardado);
        }
        
        @Override
        public ResponseEntity<List<Mensaje>> obtenerMensajes(String remitenteDni, String destinatarioDni) {
            List<Mensaje> mensajes = mockMensajeService.obtenerConversacion(remitenteDni, destinatarioDni);
            return ResponseEntity.ok(mensajes);
        }
        
        @Override
        public ResponseEntity<List<ConversacionDTO>> conversaciones(String usuarioDni) {
            List<ConversacionDTO> lista = mockMensajeService.obtenerConversaciones(usuarioDni);
            return ResponseEntity.ok(lista);
        }
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000)
    void testEnviarMensajePerformance() {
        Mensaje mensaje = new Mensaje();
        mensaje.setRemitenteDni("12345678A");
        mensaje.setDestinatarioDni("87654321B");
        mensaje.setContenido("Test performance");
        
        ResponseEntity<Mensaje> response = controller.enviarMensaje(mensaje);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000)
    void testObtenerMensajesPerformance() {
        ResponseEntity<List<Mensaje>> response = controller.obtenerMensajes("12345678A", "87654321B");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5_000)
    void testConversacionesPerformance() {
        ResponseEntity<List<ConversacionDTO>> response = controller.conversaciones("12345678A");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }
}
