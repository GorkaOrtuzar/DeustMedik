package com.mycompany.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.DTO.ConversacionDTO;
import com.mycompany.modelo.Mensaje;
import com.mycompany.service.MensajeService;
import com.mycompany.service.NotificacionService;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
public class MensajeControllerIntegrationTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private MensajeService mensajeService;

   @MockBean
   private NotificacionService notificacionService;

   @Autowired
   private ObjectMapper objectMapper;

   @Test
   public void testEnviarMensaje() throws Exception {
       Mensaje mensaje = new Mensaje();
       mensaje.setRemitenteDni("12345678A");
       mensaje.setDestinatarioDni("87654321B");
       mensaje.setContenido("Hola, ¿cómo estás?");
       mensaje.setFecha(LocalDateTime.now());

       Mensaje mensajeGuardado = new Mensaje();
       mensajeGuardado.setRemitenteDni("12345678A");
       mensajeGuardado.setDestinatarioDni("87654321B");
       mensajeGuardado.setContenido("Hola, ¿cómo estás?");
       mensajeGuardado.setFecha(LocalDateTime.now());
       
       try {
           java.lang.reflect.Field idField = Mensaje.class.getDeclaredField("id");
           idField.setAccessible(true);
           idField.set(mensajeGuardado, 1L);
       } catch (Exception e) {
       }

       when(mensajeService.guardarMensaje(any(Mensaje.class))).thenReturn(mensajeGuardado);

       mockMvc.perform(post("/api/mensajes")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(mensaje)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.remitenteDni").value("12345678A"))
               .andExpect(jsonPath("$.destinatarioDni").value("87654321B"))
               .andExpect(jsonPath("$.contenido").value("Hola, ¿cómo estás?"));

       verify(notificacionService).crearNotificacion(
               "87654321B",
               "Nuevo mensaje de 12345678A",
               "Hola, ¿cómo estás?"
       );
   }

   @Test
   public void testObtenerMensajes() throws Exception {
       String remitenteDni = "12345678A";
       String destinatarioDni = "87654321B";

       Mensaje mensaje1 = new Mensaje();
       mensaje1.setRemitenteDni(remitenteDni);
       mensaje1.setDestinatarioDni(destinatarioDni);
       mensaje1.setContenido("Hola, ¿cómo estás?");
       mensaje1.setFecha(LocalDateTime.of(2025, 5, 18, 10, 0));
       
       try {
           java.lang.reflect.Field idField = Mensaje.class.getDeclaredField("id");
           idField.setAccessible(true);
           idField.set(mensaje1, 1L);
       } catch (Exception e) {
       }

       Mensaje mensaje2 = new Mensaje();
       mensaje2.setRemitenteDni(destinatarioDni);
       mensaje2.setDestinatarioDni(remitenteDni);
       mensaje2.setContenido("Muy bien, gracias. ¿Y tú?");
       mensaje2.setFecha(LocalDateTime.of(2025, 5, 18, 10, 5));
       
       try {
           java.lang.reflect.Field idField = Mensaje.class.getDeclaredField("id");
           idField.setAccessible(true);
           idField.set(mensaje2, 2L);
       } catch (Exception e) {
       }

       List<Mensaje> mensajes = Arrays.asList(mensaje1, mensaje2);

       when(mensajeService.obtenerConversacion(remitenteDni, destinatarioDni)).thenReturn(mensajes);

       mockMvc.perform(get("/api/mensajes")
               .param("remitenteDni", remitenteDni)
               .param("destinatarioDni", destinatarioDni)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].id").value(1))
               .andExpect(jsonPath("$[0].remitenteDni").value(remitenteDni))
               .andExpect(jsonPath("$[0].destinatarioDni").value(destinatarioDni))
               .andExpect(jsonPath("$[0].contenido").value("Hola, ¿cómo estás?"))
               .andExpect(jsonPath("$[1].id").value(2))
               .andExpect(jsonPath("$[1].remitenteDni").value(destinatarioDni))
               .andExpect(jsonPath("$[1].destinatarioDni").value(remitenteDni))
               .andExpect(jsonPath("$[1].contenido").value("Muy bien, gracias. ¿Y tú?"));
   }

   @Test
   public void testConversaciones() throws Exception {
       String usuarioDni = "12345678A";

       ConversacionDTO conv1 = new ConversacionDTO("87654321B", "Juan Pérez");
       ConversacionDTO conv2 = new ConversacionDTO("11223344C", "María López");

       List<ConversacionDTO> conversaciones = Arrays.asList(conv1, conv2);

       when(mensajeService.obtenerConversaciones(usuarioDni)).thenReturn(conversaciones);

       mockMvc.perform(get("/api/mensajes/conversaciones")
               .param("usuarioDni", usuarioDni)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].dni").value("87654321B"))
               .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
               .andExpect(jsonPath("$[1].dni").value("11223344C"))
               .andExpect(jsonPath("$[1].nombre").value("María López"));
   }

   @Test
   public void testObtenerMensajes_SinMensajes() throws Exception {
       String remitenteDni = "12345678A";
       String destinatarioDni = "99999999X";

       when(mensajeService.obtenerConversacion(remitenteDni, destinatarioDni)).thenReturn(new ArrayList<>());

       mockMvc.perform(get("/api/mensajes")
               .param("remitenteDni", remitenteDni)
               .param("destinatarioDni", destinatarioDni)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$").isEmpty());
   }

   @Test
   public void testConversaciones_SinConversaciones() throws Exception {
       String usuarioDni = "12345678A";

       when(mensajeService.obtenerConversaciones(usuarioDni)).thenReturn(new ArrayList<>());

       mockMvc.perform(get("/api/mensajes/conversaciones")
               .param("usuarioDni", usuarioDni)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$").isEmpty());
   }
}