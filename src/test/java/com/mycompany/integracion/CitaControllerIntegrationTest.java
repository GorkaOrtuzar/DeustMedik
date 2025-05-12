package com.mycompany.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.DTO.ModificarCitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.mycompany.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.mycompany.service.RestApiApplication.class)
@AutoConfigureMockMvc
public class CitaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositorioCita repositorioCita;
    
    @Autowired
    private RepositorioMedico repositorioMedico;

    @MockBean
    private NotificacionService notificacionService;

    @Autowired
    private ObjectMapper objectMapper;
    private Medico medico;
    private Cita citaExistente;

    @BeforeEach
    public void setUp() {
         repositorioCita.deleteAll();

    // Crear y guardar un médico válido
    medico = new Medico();
    medico.setNombre("Dr. García");
    medico.setEspecialidad("Medicina General");
    medico = repositorioMedico.save(medico);

    // Crear cita con médico asignado
    citaExistente = new Cita();
    citaExistente.setPacienteDNI("12345678A");
    citaExistente.setFechaHora(LocalDateTime.now().plusDays(1));
    citaExistente.setMotivo("Consulta general");
    citaExistente.setMedico(medico); // ¡Aquí está la clave!
    citaExistente = repositorioCita.save(citaExistente);
    }

    @Test
    public void testCrearCita() throws Exception {
        // Crear y guardar médico
        Medico medico = new Medico();
        medico.setNombre("Dr. Gómez");
        medico.setEspecialidad("Pediatría");
        medico = repositorioMedico.save(medico);

        // Crear cita con médico asignado
        Cita nuevaCita = new Cita();
        nuevaCita.setPacienteDNI("87654321B");
        nuevaCita.setFechaHora(LocalDateTime.now().plusDays(2));
        nuevaCita.setMotivo("Revisión médica");
        nuevaCita.setMedico(medico); // este campo es obligatorio

        mockMvc.perform(post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaCita)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pacienteDNI").value("87654321B"));

        Mockito.verify(notificacionService).crearNotificacion(
                Mockito.eq("87654321B"),
                Mockito.anyString(),
                Mockito.contains("ha quedado confirmada")
        );
}

    @Test
    public void testObtenerTodasLasCitas() throws Exception {
        mockMvc.perform(get("/api"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].pacienteDNI").value(citaExistente.getPacienteDNI()));
    }
        
     @Test
    public void testActualizarCita() throws Exception {
        ModificarCitaDTO dto = new ModificarCitaDTO();
        dto.setId(citaExistente.getId());
        dto.setFechaHora(LocalDateTime.now().plusDays(5));
        dto.setMotivo("Cambio de motivo");
        
        mockMvc.perform(put("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
        
        Mockito.verify(notificacionService).crearNotificacion(
                Mockito.eq("12345678A"),
                Mockito.anyString(),
                Mockito.contains("actualizada")
            );
        }
        
    @Test
        public void testEliminarCita() throws Exception {
            mockMvc.perform(delete("/api/citas/" + citaExistente.getId()))
                    .andExpect(status().isNoContent());
        
            mockMvc.perform(delete("/api/citas/" + citaExistente.getId()))
                    .andExpect(status().isNotFound());
        }
}

    
