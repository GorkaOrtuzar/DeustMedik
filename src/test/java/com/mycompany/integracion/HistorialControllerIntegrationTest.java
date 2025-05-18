package com.mycompany.integracion;

import com.mycompany.DTO.CitaDTO;
import com.mycompany.service.HistorialService;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
public class HistorialControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistorialService historialService;

    @Test
    public void testObtenerHistorialPorDNI() throws Exception {
        String dni = "12345678A";
        List<CitaDTO> citasExpected = new ArrayList<>();
        
        when(historialService.obtenerCitasPorDni(dni)).thenReturn(citasExpected);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/autorizacion/citas/historial/{dni}", dni)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void testObtenerHistorialPorDNI_SinResultados() throws Exception {
        String dni = "87654321B";
        
        when(historialService.obtenerCitasPorDni(dni)).thenReturn(List.of());
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/autorizacion/citas/historial/{dni}", dni)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
    
    @Test
    public void testObtenerHistorialPorDNI_ErrorEnServicio() throws Exception {
        String dni = "00000000X";
        when(historialService.obtenerCitasPorDni(dni)).thenThrow(new RuntimeException("Error al obtener historial"));
        
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/autorizacion/citas/historial/{dni}", dni)
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {

        }
    }
}