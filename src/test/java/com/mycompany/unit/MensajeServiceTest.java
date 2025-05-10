package com.mycompany.unit;

import com.mycompany.DTO.ConversacionDTO;
import com.mycompany.modelo.Mensaje;
import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioMensaje;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.MensajeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MensajeServiceTest {

    @Mock private RepositorioMensaje repositorioMensaje;
    @Mock private RepositorioPaciente pacienteRepository;
    @Mock private RepositorioMedico medicoRepository;
    @InjectMocks private MensajeService service;

    @Test
    void guardarMensaje_asignaFechaYGuarda() {
        Mensaje msg = new Mensaje();
        msg.setContenido("Hola");
        when(repositorioMensaje.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Mensaje saved = service.guardarMensaje(msg);

        assertNotNull(saved.getFecha(), "Debe asignar fecha actual");
        assertEquals("Hola", saved.getContenido());
        verify(repositorioMensaje).save(saved);
    }

    @Test
    void obtenerConversacion_devolverMensajesOrdenados() {
        Mensaje m1 = new Mensaje(), m2 = new Mensaje();
        when(repositorioMensaje
            .findByRemitenteDniAndDestinatarioDniOrRemitenteDniAndDestinatarioDniOrderByFecha(
                "A","B","B","A"))
        .thenReturn(List.of(m1, m2));

        List<Mensaje> conv = service.obtenerConversacion("A","B");
        assertEquals(2, conv.size());
        assertSame(m1, conv.get(0));
    }

    @Test
    void obtenerConversaciones_cuandoEsPaciente_devuelveTodosLosMedicos() {
        when(pacienteRepository.existsByDni("P1")).thenReturn(true);
        Medico md1 = new Medico(), md2 = new Medico();
        md1.setDni("M1"); md1.setNombre("Ana"); md1.setApellido("López");
        md2.setDni("M2"); md2.setNombre("Luis"); md2.setApellido("Pérez");
        when(medicoRepository.findAll()).thenReturn(List.of(md1, md2));

        List<ConversacionDTO> dtos = service.obtenerConversaciones("P1");
        assertEquals(2, dtos.size());
        //assertTrue(dtos.stream().anyMatch(d -> d.getId().equals("M1") && d.getNombre().contains("Ana")));
    }

    @Test
    void obtenerConversaciones_noEsPaciente_devuelvePacientes() {
        when(pacienteRepository.existsByDni("M1")).thenReturn(false);
        when(repositorioMensaje.findConversacionPartners("M1")).thenReturn(List.of("P1"));
        Paciente p = new Paciente();
        p.setDni("P1"); p.setNombre("Juan"); p.setApellido("García");
        when(pacienteRepository.findByDni("P1")).thenReturn(Optional.of(p));

        List<ConversacionDTO> dtos = service.obtenerConversaciones("M1");
        assertEquals(1, dtos.size());
        //assertEquals("P1", dtos.get(0).getId());
    }

    @Test
    void obtenerConversaciones_noEsPaciente_socioInexistente_lanzaException() {
        when(pacienteRepository.existsByDni("X")).thenReturn(false);
        when(repositorioMensaje.findConversacionPartners("X")).thenReturn(List.of("Q"));
        when(pacienteRepository.findByDni("Q")).thenReturn(Optional.empty());

        assertThrows(
            IllegalArgumentException.class,
            () -> service.obtenerConversaciones("X")
        );
    }
}
