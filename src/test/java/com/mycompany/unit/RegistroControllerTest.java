package com.mycompany.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.modelo.Paciente;
import com.mycompany.service.RegistroService;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
class RegistroControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RegistroService registroService;

    @Test
    @DisplayName("GET /autorizacion/Registro → Devuelve vista Registro")
    void mostrarFormularioRegistro() throws Exception {
        mvc.perform(get("/autorizacion/Registro"))
           .andExpect(status().isOk())
           .andExpect(view().name("Registro"));
    }

    @Test
    @DisplayName("POST /autorizacion/registro → OK si se registra correctamente")
    @Transactional
    @Rollback
    void registrar_exitoso() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setDni("TEST999");
        paciente.setNombre("Juan");
        paciente.setApellido("Prueba");
        paciente.setContrasenia("1234");

        when(registroService.registrarPaciente(any(Paciente.class))).thenReturn(true);

        mvc.perform(post("/autorizacion/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(paciente)))
           .andExpect(status().isOk());

        verify(registroService).registrarPaciente(any(Paciente.class));
    }

    @Test
    @DisplayName("POST /autorizacion/registro → 400 si el DNI ya existe")
    void registrar_dniDuplicado() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setDni("DUP123");

        when(registroService.registrarPaciente(any(Paciente.class))).thenReturn(false);

        mvc.perform(post("/autorizacion/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(paciente)))
           .andExpect(status().isBadRequest())
           .andExpect(content().string("El DNI ya está registrado"));

        verify(registroService).registrarPaciente(any(Paciente.class));
    }
}
