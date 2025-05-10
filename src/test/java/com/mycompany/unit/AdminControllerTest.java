package com.mycompany.unit;

import com.mycompany.controller.AdminController;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.repositorio.RepositorioPaciente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private RepositorioMedico repositorioMedico;

    @Mock
    private RepositorioPaciente repositorioPaciente;

    @Mock
    private RepositorioCita repositorioCita;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testMostrarBaseDeDatos() {
        when(repositorioMedico.findAll()).thenReturn(List.of());
        when(repositorioPaciente.findAll()).thenReturn(List.of());
        when(repositorioCita.findAll()).thenReturn(List.of());

        String view = adminController.mostrarBaseDeDatos(model);

        assertEquals("admBD", view);
        verify(model).addAttribute(eq("medicos"), anyList());
        verify(model).addAttribute(eq("pacientes"), anyList());
        verify(model).addAttribute(eq("citas"), anyList());
    }
}
