package com.mycompany.integracion;

import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
public class MedicoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicoService medicoService;

    @Test
    public void testObtenerTodos_MedicosEncontrados() throws Exception {
        List<Medico> medicos = new ArrayList<>();
        Medico medico1 = new Medico();
        medico1.setId(1L);
        medico1.setNombre("Juan");
        
        Medico medico2 = new Medico();
        medico2.setId(2L);
        medico2.setNombre("Ana");
        
        medicos.add(medico1);
        medicos.add(medico2);
        
        when(medicoService.obtenerMedicos()).thenReturn(medicos);

        mockMvc.perform(get("/api/medicos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Ana")));
    }

    @Test
    public void testObtenerTodos_NoHayMedicos() throws Exception {
        when(medicoService.obtenerMedicos()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/medicos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerDisponibilidadMedico_HorariosDisponibles() throws Exception {
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Carlos");
        
        LocalDateTime fecha1 = LocalDateTime.of(2025, 6, 1, 10, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2025, 6, 1, 11, 30);
        List<LocalDateTime> disponibilidad = Arrays.asList(fecha1, fecha2);
        
        when(medicoService.buscarPorNombreYApellido(anyString(), anyString())).thenReturn(medico);
        when(medicoService.obtenerDisponibilidadMedico(anyLong())).thenReturn(disponibilidad);

        mockMvc.perform(post("/api/medicos/medico/disponibilidad")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Carlos\",\"apelliddo\":\"García\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty());
    }

    @Test
    public void testObtenerDisponibilidadMedico_SinHorariosDisponibles() throws Exception {
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Carlos");
        
        when(medicoService.buscarPorNombreYApellido(anyString(), anyString())).thenReturn(medico);
        when(medicoService.obtenerDisponibilidadMedico(anyLong())).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/api/medicos/medico/disponibilidad")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Carlos\",\"apelliddo\":\"García\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerHorarioPorNombreYApellido_MedicoEncontrado() throws Exception {
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Elena");
        
        LocalDateTime fecha1 = LocalDateTime.of(2025, 6, 2, 9, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2025, 6, 2, 10, 30);
        List<LocalDateTime> disponibilidad = Arrays.asList(fecha1, fecha2);
        
        when(medicoService.buscarPorNombreYApellido("Elena", "Pérez")).thenReturn(medico);
        when(medicoService.obtenerDisponibilidadMedico(medico.getId())).thenReturn(disponibilidad);

        mockMvc.perform(get("/api/medicos/medico/Horario")
                .param("Nombre", "Elena")
                .param("Apellido", "Pérez")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testObtenerHorarioPorNombreYApellido_MedicoNoEncontrado() throws Exception {
        when(medicoService.buscarPorNombreYApellido("Desconocido", "Apellido")).thenReturn(null);
        mockMvc.perform(get("/api/medicos/medico/Horario")
                .param("Nombre", "Desconocido")
                .param("Apellido", "Apellido")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testMostrarPaginaBusqueda() throws Exception {
        mockMvc.perform(get("/api/medicos/buscar-medicos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("buscar-medicos"));
    }
}