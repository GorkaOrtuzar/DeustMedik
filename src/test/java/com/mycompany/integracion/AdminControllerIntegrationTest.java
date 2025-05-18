package com.mycompany.integracion;

import com.mycompany.controller.AdminController;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.RestApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositorioMedico repositorioMedico;

    @MockBean
    private RepositorioPaciente repositorioPaciente;

    @MockBean
    private RepositorioCita repositorioCita;

    @Test
    public void testMostrarBaseDeDatos() throws Exception {
        // Arrange
        // Crear médicos primero
        Medico medico1 = new Medico();
        medico1.setId(1L);
        medico1.setNombre("Carlos");
        medico1.setApellido("García");  // Asumiendo que existe setApellido
        
        Medico medico2 = new Medico();
        medico2.setId(2L);
        medico2.setNombre("Ana");
        medico2.setApellido("López");   // Asumiendo que existe setApellido
        
        List<Medico> medicos = new ArrayList<>();
        medicos.add(medico1);
        medicos.add(medico2);
        
        // Crear pacientes
        Paciente paciente1 = new Paciente();
        paciente1.setDni("12345678A");
        paciente1.setNombre("Juan");
        paciente1.setApellido("Pérez");  // Asumiendo que existe setApellido
        
        Paciente paciente2 = new Paciente();
        paciente2.setDni("87654321B");
        paciente2.setNombre("María");
        paciente2.setApellido("Gómez");  // Asumiendo que existe setApellido
        
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(paciente1);
        pacientes.add(paciente2);
        
        // Crear citas con referencia a médicos y datos de paciente como strings
        Cita cita1 = new Cita();
        cita1.setId(1L);
        cita1.setFechaHora(LocalDateTime.of(2025, 6, 1, 10, 0));
        cita1.setMedico(medico1);  // Asignar médico a la cita
        cita1.setPacienteDNI(paciente1.getDni());  // Asignar DNI del paciente
        cita1.setPacienteNombre(paciente1.getNombre());  // Asignar nombre del paciente
        
        Cita cita2 = new Cita();
        cita2.setId(2L);
        cita2.setFechaHora(LocalDateTime.of(2025, 6, 2, 15, 30));
        cita2.setMedico(medico2);  // Asignar médico a la cita
        cita2.setPacienteDNI(paciente2.getDni());  // Asignar DNI del paciente
        cita2.setPacienteNombre(paciente2.getNombre());  // Asignar nombre del paciente
        
        List<Cita> citas = new ArrayList<>();
        citas.add(cita1);
        citas.add(cita2);
        
        // Configurar comportamiento de los mocks
        when(repositorioMedico.findAll()).thenReturn(medicos);
        when(repositorioPaciente.findAll()).thenReturn(pacientes);
        when(repositorioCita.findAll()).thenReturn(citas);

        // Act & Assert
        mockMvc.perform(get("/admBD"))
                .andExpect(status().isOk())
                .andExpect(view().name("admBD"))
                .andExpect(model().attribute("medicos", medicos))
                .andExpect(model().attribute("pacientes", pacientes))
                .andExpect(model().attribute("citas", citas));
    }
    
    @Test
    public void testMostrarBaseDeDatosVacia() throws Exception {
        // Arrange
        // Configurar repositorios para que devuelvan listas vacías
        when(repositorioMedico.findAll()).thenReturn(new ArrayList<>());
        when(repositorioPaciente.findAll()).thenReturn(new ArrayList<>());
        when(repositorioCita.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/admBD"))
                .andExpect(status().isOk())
                .andExpect(view().name("admBD"))
                .andExpect(model().attribute("medicos", new ArrayList<>()))
                .andExpect(model().attribute("pacientes", new ArrayList<>()))
                .andExpect(model().attribute("citas", new ArrayList<>()));
    }
}