package com.mycompany.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.modelo.Mensaje;
import com.mycompany.DTO.ConversacionDTO;
import com.mycompany.service.MensajeService;
import com.mycompany.service.NotificacionService;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
class MensajeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MensajeService mensajeService;

    @MockBean
    private NotificacionService notificacionService;

    @Test
    void enviarMensaje_deberiaGuardarYNotificar() throws Exception {
        Mensaje mensaje = new Mensaje();
        mensaje.setRemitenteDni("123A");
        mensaje.setDestinatarioDni("456B");
        mensaje.setContenido("Hola");

        when(mensajeService.guardarMensaje(any())).thenReturn(mensaje);

        mvc.perform(post("/api/mensajes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mensaje)))
           .andExpect(status().isOk());

        verify(mensajeService).guardarMensaje(any());
        verify(notificacionService).crearNotificacion(eq("456B"), contains("123A"), eq("Hola"));
    }

    @Test
    void obtenerMensajes_deberiaRetornarConversacion() throws Exception {
        when(mensajeService.obtenerConversacion("123A", "456B")).thenReturn(List.of(new Mensaje()));

        mvc.perform(get("/api/mensajes")
                .param("remitenteDni", "123A")
                .param("destinatarioDni", "456B"))
           .andExpect(status().isOk());

        verify(mensajeService).obtenerConversacion("123A", "456B");
    }

    @Test
    void conversaciones_deberiaRetornarDTOs() throws Exception {
        when(mensajeService.obtenerConversaciones("123A")).thenReturn(List.of(new ConversacionDTO()));

        mvc.perform(get("/api/mensajes/conversaciones")
                .param("usuarioDni", "123A"))
           .andExpect(status().isOk());

        verify(mensajeService).obtenerConversaciones("123A");
    }
}

