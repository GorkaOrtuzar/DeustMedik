package com.mycompany.unit;

import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.RegistroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegistroServiceTest {

    private RegistroService registroService;
    private RepositorioPaciente repositorioPacienteMock;

    @BeforeEach
    void setUp() {
        repositorioPacienteMock = mock(RepositorioPaciente.class);
        registroService = new RegistroService(repositorioPacienteMock);
    }

    @Test
    void registrarPaciente_DeberiaRegistrarSiNoExisteDni() {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678A");

        when(repositorioPacienteMock.existsByDni("12345678A")).thenReturn(false);

        boolean resultado = registroService.registrarPaciente(paciente);

        assertTrue(resultado);
        verify(repositorioPacienteMock, times(1)).save(paciente);
    }

    @Test
    void registrarPaciente_NoDeberiaRegistrarSiExisteDni() {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678A");

        when(repositorioPacienteMock.existsByDni("12345678A")).thenReturn(true);

        boolean resultado = registroService.registrarPaciente(paciente);

        assertFalse(resultado);
        verify(repositorioPacienteMock, never()).save(any(Paciente.class));
    }
}
