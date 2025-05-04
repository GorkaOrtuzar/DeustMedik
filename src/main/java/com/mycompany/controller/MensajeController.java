package com.mycompany.controller;

import com.mycompany.modelo.Mensaje;
import com.mycompany.service.MensajeService;
import com.mycompany.service.NotificacionService;
import com.mycompany.DTO.ConversacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired 
    private NotificacionService notiService;

    @PostMapping
    public ResponseEntity<Mensaje> enviarMensaje(@RequestBody Mensaje mensaje) {
        System.out.println("📥 Recibido: " + mensaje.getRemitenteDni() + " → " + mensaje.getDestinatarioDni());
        Mensaje guardado = mensajeService.guardarMensaje(mensaje);
        notiService.crearNotificacion(
            guardado.getDestinatarioDni(),
            "Nuevo mensaje de " + guardado.getRemitenteDni(),
            guardado.getContenido()
        );
        return ResponseEntity.ok(guardado);
    }

    @GetMapping
    public ResponseEntity<List<Mensaje>> obtenerMensajes(
        @RequestParam("remitenteDni") String remitenteDni,
        @RequestParam("destinatarioDni") String destinatarioDni) {

        List<Mensaje> mensajes = mensajeService.obtenerConversacion(remitenteDni, destinatarioDni);
        return ResponseEntity.ok(mensajes);
    }

    @GetMapping("/conversaciones")
    public ResponseEntity<List<ConversacionDTO>> conversaciones(
        @RequestParam("usuarioDni") String usuarioDni) {

        List<ConversacionDTO> lista = mensajeService.obtenerConversaciones(usuarioDni);
        return ResponseEntity.ok(lista);
    }
}
