package com.mycompany.integracion;

import com.mycompany.modelo.Paciente;
import com.mycompany.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.mycompany.service.RestApiApplication.class)
@AutoConfigureMockMvc
public class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Test
    public void testRedirigirRaiz() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testMostrarLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testLoginExitoso() throws Exception {
        Paciente mockPaciente = new Paciente();
        mockPaciente.setDni("12345678");
        mockPaciente.setContrasenia("password");

        when(pacienteService.getPacienteByDNI("12345678")).thenReturn(mockPaciente);

        mockMvc.perform(post("/login")
                        .param("dni", "12345678")
                        .param("contraseña", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("inicio"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testLoginFallido() throws Exception {
        when(pacienteService.getPacienteByDNI("12345678")).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("dni", "12345678")
                        .param("contraseña", "wrongpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }
}
