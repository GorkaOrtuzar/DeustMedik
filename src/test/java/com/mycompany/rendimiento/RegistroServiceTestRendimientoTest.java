package com.mycompany.rendimiento;

import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.RegistroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegistroServiceTestRendimientoTest {

    private RepositorioPaciente repositorioPaciente;
    private RegistroService registroService;

    @BeforeEach
    public void setup() {
        repositorioPaciente = mock(RepositorioPaciente.class);
        registroService = new RegistroService(repositorioPaciente);
    }

    @Test
    public void testRegistrarPacientePerformance() {
        Paciente paciente = new Paciente();
        paciente.setDni("87654321");
        paciente.setNombre("Juan");
        paciente.setContrasenia("segura123");

        when(repositorioPaciente.existsByDni("87654321")).thenReturn(false);
        when(repositorioPaciente.save(paciente)).thenReturn(paciente);

        long startTime = System.nanoTime();
        boolean result = registroService.registrarPaciente(paciente);
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000;

        System.out.println("Duración registrarPaciente(): " + durationMs + " ms");

        assertTrue(result, "El paciente debería haberse registrado correctamente");
        assertTrue(durationMs < 100, "registrarPaciente() debería ejecutarse en menos de 100 ms");
    }

    @Test
    public void testRegistrarPacienteYaExistentePerformance() {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678");

        when(repositorioPaciente.existsByDni("12345678")).thenReturn(true);

        long startTime = System.nanoTime();
        boolean result = registroService.registrarPaciente(paciente);
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000;

        System.out.println("Duración registrarPaciente() cuando ya existe: " + durationMs + " ms");

        assertFalse(result, "No se debería registrar un paciente ya existente");
        assertTrue(durationMs < 100, "La verificación de existencia debería ser rápida (<100 ms)");
    }
}

