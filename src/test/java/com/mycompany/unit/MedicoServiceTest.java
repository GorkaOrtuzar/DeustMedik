package com.mycompany.unit;

import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.service.MedicoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {

    @Mock
    private RepositorioMedico repositorioMedico;
    @Mock
    private RepositorioCita repositorioCita;
    @InjectMocks
    private MedicoService service;

    @Test
    void obtenerMedicos_devuelveListaDesdeRepositorio() {
        Medico m1 = new Medico();
        Medico m2 = new Medico();
        when(repositorioMedico.findAll()).thenReturn(List.of(m1, m2));

        List<Medico> result = service.obtenerMedicos();

        assertEquals(2, result.size());
        assertSame(m1, result.get(0));
        assertSame(m2, result.get(1));
        verify(repositorioMedico).findAll();
    }

    @Test
    void obtenerDisponibilidadMedico_medicoNoExiste_devuelveListaVacia() {
        when(repositorioMedico.findById(100L)).thenReturn(Optional.empty());

        List<LocalDateTime> slots = service.obtenerDisponibilidadMedico(100L);

        assertTrue(slots.isEmpty(), "Si no encuentra al médico debe devolver lista vacía");
        verify(repositorioMedico).findById(100L);
    }

    @Test
    void obtenerDisponibilidadMedico_medicoExiste_generaSlotsCorrectos() {
        when(repositorioMedico.findById(1L)).thenReturn(Optional.of(new Medico()));

        List<LocalDateTime> slots = service.obtenerDisponibilidadMedico(1L);

        assertEquals(32, slots.size());

        LocalDate today = LocalDate.now();
        LocalDateTime primero = LocalDateTime.of(today, LocalTime.of(9, 0));
        LocalDateTime ultimo = LocalDateTime.of(today, LocalTime.of(16, 45));

        assertEquals(primero, slots.get(0));
        assertEquals(ultimo, slots.get(slots.size() - 1));
        verify(repositorioMedico).findById(1L);
    }

    @Test
    void buscarPorNombreYApellido_delegadoAlRepositorio() {
        Medico esperado = new Medico();
        when(repositorioMedico.findByNombreAndApellido("Ana", "López"))
            .thenReturn(esperado);

        Medico result = service.buscarPorNombreYApellido("Ana", "López");

        assertSame(esperado, result);
        verify(repositorioMedico).findByNombreAndApellido("Ana", "López");
    }
}
