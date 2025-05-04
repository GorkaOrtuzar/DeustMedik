package com.mycompany.controller;

import com.mycompany.modelo.Notificacion;
import com.mycompany.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    public ResponseEntity<List<Notificacion>> listar(
        @RequestParam("usuarioDni") String usuarioDni,
        @RequestParam(name = "desde", required = false)
        @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime desde,
        @RequestParam(name = "hasta", required = false)
        @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime hasta
    ) {
        List<Notificacion> lista = service.obtenerNotificaciones(usuarioDni, desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/bienvenida")
    public ResponseEntity<Notificacion> bienvenida(
        @RequestParam("usuarioDni") String usuarioDni
    ) {
        Notificacion n = service.crearNotificacion(
            usuarioDni,
            "Bienvenido de nuevo",
            "¡Hola! Te damos la bienvenida a nuestro sistema médico."
        );
        return ResponseEntity.ok(n);
    }
}
