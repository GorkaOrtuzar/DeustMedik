package com.mycompany.rendimiento;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.HistorialService;
import com.mycompany.DTO.CitaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistorialServicePerformanceTest {

    @Mock
    private RepositorioCita repositorioCita;

    @Mock
    private RepositorioPaciente repositorioPaciente;

    @InjectMocks
    private HistorialService historialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void testObtenerCitasGranVolumen() {
        List<Cita> citasSimuladas = new ArrayList<>();
        
        // Crear 1000 citas de prueba
        for (int i = 0; i < 1000; i++) {
            Medico medico = new Medico();
            medico.setNombre("Juan" + i);
            medico.setApellido("Pérez" + i);
            medico.setEspecialidad("Especialidad" + i);

            Cita cita = new Cita();
            cita.setFechaHora(LocalDateTime.now().plusDays(i));
            cita.setMotivo("Motivo" + i);
            cita.setMedico(medico);
            citasSimuladas.add(cita);
        }

        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("12345678A"))
            .thenReturn(citasSimuladas);

        long startTime = System.currentTimeMillis();
        List<CitaDTO> resultado = historialService.obtenerCitasPorDni("12345678A");
        long endTime = System.currentTimeMillis();

        assertEquals(1000, resultado.size());
        long duration = endTime - startTime;
        System.out.println("Tiempo para convertir 1000 citas a DTO: " + duration + "ms");
        assertTrue(duration < 1000, "La conversión debe ser menor a 1 segundo");
    }


    @Test
    void testMultiplesBusquedasRendimiento() {
        Medico medico = new Medico();
        medico.setNombre("Juan");
        medico.setApellido("Pérez");
        medico.setEspecialidad("Cardiología");

        Cita cita = new Cita();
        cita.setFechaHora(LocalDateTime.now());
        cita.setMotivo("Consulta");
        cita.setMedico(medico);

        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc(anyString()))
            .thenReturn(List.of(cita));

        long startTime = System.currentTimeMillis();
        
        // Realizar 500 búsquedas de historial
        for (int i = 0; i < 500; i++) {
            historialService.obtenerCitasPorDni("DNI" + i);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Tiempo para 500 búsquedas de historial: " + duration + "ms");
        assertTrue(duration < 3000, "500 búsquedas deben completarse en menos de 3 segundos");
    }
}