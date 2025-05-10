package com.mycompany.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.DTO.ModificarCitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.service.NotificacionService;
import com.mycompany.service.RestApiApplication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
class CitaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RepositorioCita repositorioCita;
    @MockBean
    private NotificacionService notiService;

    @DisplayName("PUT /api/citas → 200 si la cita existe y se actualiza correctamente")
    @Test
    void actualizarCita_existe_devuelve200yGuardaYNotifica() throws Exception {
        ModificarCitaDTO dto = new ModificarCitaDTO();
        dto.setId(1L);
        LocalDateTime nuevaFecha = LocalDateTime.of(2025,5,10,15,0);
        dto.setFechaHora(nuevaFecha);
        dto.setMotivo("Cita de prueba");

        Cita existente = new Cita();
        existente.setId(1L);
        existente.setPacienteDNI("PAC123");
        existente.setFechaHora(LocalDateTime.of(2025,1,1,9,0));
        existente.setMotivo("Anterior");
        when(repositorioCita.findById(1L)).thenReturn(Optional.of(existente));

        mvc.perform(put("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
        )
        .andExpect(status().isOk());

        ArgumentCaptor<Cita> captor = ArgumentCaptor.forClass(Cita.class);
        then(repositorioCita).should().save(captor.capture());
        Cita guardada = captor.getValue();
        assertThat(guardada.getId()).isEqualTo(1L);
        assertThat(guardada.getFechaHora()).isEqualTo(nuevaFecha);
        assertThat(guardada.getMotivo()).isEqualTo("Cita de prueba");

        then(notiService).should().crearNotificacion(
                eq("PAC123"),
                eq("Cita programada"),
                contains("ha quedado actualizada")
        );
    }

    @DisplayName("PUT /api/citas → 404 si la cita NO existe")
    @Test
    void actualizarCita_noExiste_devuelve404() throws Exception {
        ModificarCitaDTO dto = new ModificarCitaDTO();
        dto.setId(99L);
        dto.setFechaHora(LocalDateTime.now());
        dto.setMotivo("No importa");

        when(repositorioCita.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(put("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto))
        )
        .andExpect(status().isNotFound());

        then(repositorioCita).should(never()).save(any());
        then(notiService).should(never()).crearNotificacion(anyString(), anyString(), anyString());
    }

    @DisplayName("DELETE /api/citas/{id} → 204 si la cita existe y se elimina")
    @Test
    void eliminarCita_existe_devuelve204yElimina() throws Exception {
        when(repositorioCita.existsById(5L)).thenReturn(true);

        mvc.perform(delete("/api/citas/5"))
           .andExpect(status().isNoContent());

        then(repositorioCita).should().deleteById(5L);
        then(notiService).shouldHaveNoInteractions();
    }

    @DisplayName("DELETE /api/citas/{id} → 404 si la cita NO existe")
    @Test
    void eliminarCita_noExiste_devuelve404() throws Exception {
        when(repositorioCita.existsById(42L)).thenReturn(false);

        mvc.perform(delete("/api/citas/42"))
           .andExpect(status().isNotFound());

        then(repositorioCita).should(never()).deleteById(anyLong());
        then(notiService).shouldHaveNoInteractions();
    }

    @DisplayName("POST /api/citas → 200 si se crea correctamente y notifica")
    @Test
    void crearCita_devuelve200yGuardaYNotifica() throws Exception {
        Cita nueva = new Cita();
        nueva.setId(10L);
        nueva.setPacienteDNI("PAC999");
        nueva.setFechaHora(LocalDateTime.of(2025, 6, 1, 10, 0));
        nueva.setMotivo("Revisión general");

        when(repositorioCita.save(any(Cita.class))).thenReturn(nueva);

        mvc.perform(
                post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(nueva))
        ).andExpect(status().isOk());

        then(repositorioCita).should().save(any(Cita.class));
        then(notiService).should().crearNotificacion(
                eq("PAC999"),
                eq("Cita programada"),
                contains("ha quedado confirmada")
        );
    }
}
