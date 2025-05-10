package com.mycompany.unit;

import com.mycompany.DTO.CredencialesDTO;
import com.mycompany.modelo.Paciente;
import com.mycompany.service.PacienteService;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
class PacienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PacienteService pacienteService;

    @Test
    void login_credencialesValidas_devuelve200() throws Exception {
        CredencialesDTO dto = new CredencialesDTO();
        dto.setDni("111A");
        dto.setContrasenia("1234");

        when(pacienteService.login("111A", "1234")).thenReturn(Optional.of("token123"));

        mvc.perform(post("/autorizacion/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isOk())
           .andExpect(content().string("token123"));
    }

    @Test
    void login_credencialesInvalidas_devuelve401() throws Exception {
        CredencialesDTO dto = new CredencialesDTO();
        dto.setDni("111A");
        dto.setContrasenia("wrong");

        when(pacienteService.login("111A", "wrong")).thenReturn(Optional.empty());

        mvc.perform(post("/autorizacion/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
           .andExpect(status().isUnauthorized());
    }

    @Test
    void perfil_existente_devuelvePaciente() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setDni("111A");

        when(pacienteService.getPacienteByDNI("111A")).thenReturn(paciente);

        mvc.perform(get("/autorizacion/perfil")
                .param("dni", "111A"))
           .andExpect(status().isOk());
    }

    @Test
    void perfil_noExistente_devuelve404() throws Exception {
        when(pacienteService.getPacienteByDNI("222B")).thenReturn(null);

        mvc.perform(get("/autorizacion/perfil")
                .param("dni", "222B"))
           .andExpect(status().isNotFound());
    }
}
