package com.mycompany.integracion;

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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
public class PacienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Test
    public void testLogin_Exitoso() throws Exception {
        String dni = "12345678A";
        String contrasenia = "password123";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."; // Token JWT de ejemplo
        
        when(pacienteService.login(dni, contrasenia)).thenReturn(Optional.of(token));
        
        mockMvc.perform(post("/autorizacion/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dni\":\"" + dni + "\",\"contrasenia\":\"" + contrasenia + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }
    
    @Test
    public void testLogin_Fallido() throws Exception {
        String dni = "12345678A";
        String contrasenia = "passwordIncorrecta";
        
        when(pacienteService.login(dni, contrasenia)).thenReturn(Optional.empty());
        
        mockMvc.perform(post("/autorizacion/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"dni\":\"" + dni + "\",\"contrasenia\":\"" + contrasenia + "\"}"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void testPerfil_PacienteEncontrado() throws Exception {
        String dni = "12345678A";
        Paciente paciente = new Paciente();
        paciente.setDni(dni);
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        
        when(pacienteService.getPacienteByDNI(dni)).thenReturn(paciente);
        
        mockMvc.perform(get("/autorizacion/perfil")
                .param("dni", dni)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dni").value(dni))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"));
    }
    
    @Test
    public void testPerfil_PacienteNoEncontrado() throws Exception {
        String dniNoExistente = "99999999X";
        when(pacienteService.getPacienteByDNI(dniNoExistente)).thenReturn(null);
        mockMvc.perform(get("/autorizacion/perfil")
                .param("dni", dniNoExistente)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testLogin_ErrorServidor() throws Exception {
        String dni = "12345678A";
        String contrasenia = "password123";
        
        when(pacienteService.login(anyString(), anyString())).thenThrow(new RuntimeException("Error de conexión a la base de datos"));
        
        try {
            mockMvc.perform(post("/autorizacion/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"dni\":\"" + dni + "\",\"contrasenia\":\"" + contrasenia + "\"}"));
        } catch (Exception e) {
           
        }
    }
}