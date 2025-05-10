package com.mycompany.unit;

import com.mycompany.modelo.Notificacion;
import com.mycompany.repositorio.RepositorioNotificacion;
import com.mycompany.service.NotificacionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock private RepositorioNotificacion repo;
    @InjectMocks private NotificacionService service;

    @Test
    void crearNotificacion_pueblaCamposYGuarda() {
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Notificacion n = service.crearNotificacion("U1","Hola","Cuerpo");
        assertEquals("U1", n.getUsuarioDni());
        assertEquals("Hola", n.getTitulo());
        assertEquals("Cuerpo", n.getCuerpo());
        assertNotNull(n.getFecha());
        verify(repo).save(n);
    }

    @Test
    void obtenerNotificaciones_sinFechas_devuelveTodas() {
        List<Notificacion> list = List.of(new Notificacion());
        when(repo.findByUsuarioDniOrderByFechaDesc("U1")).thenReturn(list);

        List<Notificacion> result = service.obtenerNotificaciones("U1", null, null);
        assertSame(list, result);
    }

    @Test
    void obtenerNotificaciones_conFechas_devuelveRango() {
        LocalDateTime desde = LocalDateTime.now().minusDays(1);
        LocalDateTime hasta = LocalDateTime.now();
        List<Notificacion> list = List.of(new Notificacion());
        when(repo.findByUsuarioDniAndFechaBetweenOrderByFechaDesc("U1", desde, hasta))
            .thenReturn(list);

        List<Notificacion> result = service.obtenerNotificaciones("U1", desde, hasta);
        assertSame(list, result);
    }
}

