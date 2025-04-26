package com.mycompany.unit;

import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.service.MedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedicoServiceTest {

    private RepositorioMedico repositorioMedico;
    private RepositorioCita repositorioCita;
    private MedicoService medicoService;

    @BeforeEach
    void setUp() {
        repositorioMedico = mock(RepositorioMedico.class);
        repositorioCita = mock(RepositorioCita.class);
        medicoService = new MedicoService(repositorioMedico, repositorioCita);
    }

    @Test
    void testObtenerMedicos() {
        Medico medico = new Medico();
        when(repositorioMedico.findAll()).thenReturn(Collections.singletonList(medico));

        List<Medico> resultado = medicoService.obtenerMedicos();

        assertEquals(1, resultado.size());
        verify(repositorioMedico, times(1)).findAll();
    }

    @Test
    void testBuscarPorNombreYApellido() {
        Medico medico = new Medico();
        when(repositorioMedico.findByNombreAndApellido("Juan", "Pérez")).thenReturn(medico);

        Medico resultado = medicoService.buscarPorNombreYApellido("Juan", "Pérez");

        assertNotNull(resultado);
        verify(repositorioMedico, times(1)).findByNombreAndApellido("Juan", "Pérez");
    }

    @Test
    void testObtenerDisponibilidadMedicoMedicoNoEncontrado() {
        when(repositorioMedico.findById(1L)).thenReturn(Optional.empty());

        List<?> disponibilidad = medicoService.obtenerDisponibilidadMedico(1L);

        assertTrue(disponibilidad.isEmpty());
        verify(repositorioMedico, times(1)).findById(1L);
    }
}
