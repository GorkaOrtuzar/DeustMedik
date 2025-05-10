package com.mycompany.unit;

import com.mycompany.modelo.Notificacion;
import com.mycompany.service.NotificacionService;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
class NotificacionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NotificacionService notificacionService;

    @Test
    void listar_deberiaRetornarNotificaciones() throws Exception {
        when(notificacionService.obtenerNotificaciones(any(), any(), any()))
            .thenReturn(List.of(new Notificacion()));

        mvc.perform(get("/api/notificaciones")
                .param("usuarioDni", "123A")
                .param("desde", "2024-01-01T00:00:00")
                .param("hasta", "2025-01-01T00:00:00"))
           .andExpect(status().isOk());

        verify(notificacionService).obtenerNotificaciones(any(), any(), any());
    }

    @Test
    void bienvenida_deberiaCrearNotificacion() throws Exception {
        when(notificacionService.crearNotificacion(any(), any(), any()))
            .thenReturn(new Notificacion());

        mvc.perform(post("/api/notificaciones/bienvenida")
                .param("usuarioDni", "123A"))
           .andExpect(status().isOk());

        verify(notificacionService).crearNotificacion(eq("123A"), any(), any());
    }
}

