package com.mycompany.rendimiento;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PacienteServiceTestRendimientoTest {

    private PacienteService pacienteService;
    private RepositorioPaciente repositorioPaciente;
    private RepositorioCita repositorioCita;

    @BeforeEach
    public void setup() {
        repositorioPaciente = mock(RepositorioPaciente.class);
        repositorioCita = mock(RepositorioCita.class);
        pacienteService = new PacienteService(repositorioPaciente, repositorioCita);
    }

    @Test
    public void testLoginPerformance() {
        Paciente mockPaciente = new Paciente();
        mockPaciente.setDni("12345678");
        mockPaciente.setContrasenia("password");

        when(repositorioPaciente.findByDni("12345678")).thenReturn(Optional.of(mockPaciente));

        long startTime = System.nanoTime();
        Optional<String> result = pacienteService.login("12345678", "password");
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000; // milisegundos

        System.out.println("Duración login(): " + durationMs + " ms");

        assertTrue(result.isPresent());
        assertTrue(durationMs < 100, "El método login() debería ejecutarse en menos de 100 ms");
    }

    @Test
    public void testObtenerHistorialCitasPerformance() {
        Paciente mockPaciente = new Paciente();
        mockPaciente.setDni("12345678");

        List<Cita> citas = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Cita cita = new Cita();
            cita.setFechaHora(LocalDateTime.now().minusDays(i));
            citas.add(cita);
        }

        when(repositorioPaciente.findByDni("12345678")).thenReturn(Optional.of(mockPaciente));
        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("12345678")).thenReturn(citas);

        long startTime = System.nanoTime();
        List<Cita> result = pacienteService.obtenerHistorialCitas("12345678");
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000;

        System.out.println("Duración obtenerHistorialCitas(): " + durationMs + " ms");

        assertEquals(1000, result.size());
        assertTrue(durationMs < 200, "El método obtenerHistorialCitas() debería ejecutarse en menos de 200 ms");
    }
}
