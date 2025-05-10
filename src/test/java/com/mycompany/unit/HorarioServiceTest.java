package com.mycompany.unit;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioHorario;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.service.HorarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioServiceTest {

    @Mock private RepositorioHorario repositorioHorario;
    @Mock private RepositorioMedico repositorioMedico;
    @InjectMocks private HorarioService service;

    @Test
    void obtenerHorarioMedico_medicoNoExiste_devuelveListaVacia() {
        when(repositorioMedico.findByNombre("X")).thenReturn(Optional.empty());
        List<Horario> result = service.obtenerHorarioMedico("X");
        assertTrue(result.isEmpty());
        verify(repositorioHorario, never()).findByMedico(any());
    }

    @Test
    void obtenerHorarioMedico_medicoExiste_devuelveHorarios() {
        Medico m = new Medico();
        when(repositorioMedico.findByNombre("Ana")).thenReturn(Optional.of(m));
        List<Horario> expected = List.of(new Horario(), new Horario());
        when(repositorioHorario.findByMedico(m)).thenReturn(expected);

        List<Horario> result = service.obtenerHorarioMedico("Ana");

        assertEquals(expected, result);
        verify(repositorioHorario).findByMedico(m);
    }

    @Test
    void obtenerHorariosPorMedico_idNoExiste_devuelveVacia() {
        when(repositorioMedico.findById(99L)).thenReturn(Optional.empty());
        List<Horario> result = service.obtenerHorariosPorMedico(99L);
        assertTrue(result.isEmpty());
    }

    @Test
    void obtenerHorariosPorMedico_idExiste_devuelveHorarios() {
        Medico m = new Medico();
        when(repositorioMedico.findById(1L)).thenReturn(Optional.of(m));
        List<Horario> expected = List.of(new Horario());
        when(repositorioHorario.findByMedico(m)).thenReturn(expected);

        List<Horario> result = service.obtenerHorariosPorMedico(1L);

        assertEquals(expected, result);
    }
}
