package com.mycompany.service;

import com.mycompany.modelo.Notificacion;
import com.mycompany.repositorio.RepositorioNotificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private RepositorioNotificacion repo;

    public Notificacion crearNotificacion(String usuarioDni, String titulo, String cuerpo) {
        Notificacion n = new Notificacion();
        n.setUsuarioDni(usuarioDni);
        n.setTitulo(titulo);
        n.setCuerpo(cuerpo);
        n.setFecha(LocalDateTime.now());
        return repo.save(n);
    }

    public List<Notificacion> obtenerNotificaciones(
        String usuarioDni, LocalDateTime desde, LocalDateTime hasta) {
        if (desde != null && hasta != null) {
            return repo.findByUsuarioDniAndFechaBetweenOrderByFechaDesc(usuarioDni, desde, hasta);
        }
        return repo.findByUsuarioDniOrderByFechaDesc(usuarioDni);
    }
}