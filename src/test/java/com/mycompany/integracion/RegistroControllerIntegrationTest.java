package com.mycompany.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.modelo.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.mycompany.service.RestApiApplication.class)
@AutoConfigureMockMvc
public class RegistroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registrarPacienteTest() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678Z");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setCorreo("juan.perez@example.com");
        paciente.setContrasenia("password123");

        mockMvc.perform(post("/autorizacion/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk());
    }
}

