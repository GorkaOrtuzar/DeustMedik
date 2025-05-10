package com.mycompany.unit;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.PacienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock private RepositorioPaciente repositorioPaciente;
    @Mock private RepositorioCita repositorioCita;
    @InjectMocks private PacienteService service;

    @Test
    void login_conCredencialesCorrectas_devuelveDNI() {
        Paciente p = new Paciente();
        p.setDni("P1");
        p.setContrasenia("pass");
        when(repositorioPaciente.findByDni("P1")).thenReturn(Optional.of(p));

        Optional<String> res = service.login("P1","pass");
        assertTrue(res.isPresent());
        assertEquals("P1", res.get());
    }

    @Test
    void login_conPasswordErronea_devuelveEmpty() {
        Paciente p = new Paciente();
        p.setDni("P1"); p.setContrasenia("xyz");
        when(repositorioPaciente.findByDni("P1")).thenReturn(Optional.of(p));

        assertTrue(service.login("P1","no").isEmpty());
    }

    @Test
    void login_usuarioNoExiste_devuelveEmpty() {
        when(repositorioPaciente.findByDni("Q")).thenReturn(Optional.empty());
        assertTrue(service.login("Q","any").isEmpty());
    }

    @Test
    void getPacienteByDNI_existente_devuelvePaciente() {
        Paciente p = new Paciente();
        when(repositorioPaciente.findByDni("P2")).thenReturn(Optional.of(p));

        assertSame(p, service.getPacienteByDNI("P2"));
    }

    @Test
    void getPacienteByDNI_noExistente_devuelveNull() {
        when(repositorioPaciente.findByDni("X")).thenReturn(Optional.empty());
        assertNull(service.getPacienteByDNI("X"));
    }

    @Test
    void obtenerHistorialCitas_pacienteNoExiste_devuelveVacio() {
        when(repositorioPaciente.findByDni("Z")).thenReturn(Optional.empty());
        List<Cita> res = service.obtenerHistorialCitas("Z");
        assertTrue(res.isEmpty());
    }

    @Test
    void obtenerHistorialCitas_pacienteExiste_devuelveCitasOrdenadas() {
        Paciente p = new Paciente();
        p.setDni("P3");
        when(repositorioPaciente.findByDni("P3")).thenReturn(Optional.of(p));
        List<Cita> citas = List.of(new Cita(), new Cita());
        when(repositorioCita.findByPacienteDNIOrderByFechaHoraDesc("P3")).thenReturn(citas);

        List<Cita> res = service.obtenerHistorialCitas("P3");
        assertEquals(citas, res);
    }
}

