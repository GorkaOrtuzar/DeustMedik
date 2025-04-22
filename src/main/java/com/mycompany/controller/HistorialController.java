package com.mycompany.controller;

import com.mycompany.modelo.Cita;
import com.mycompany.service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autorizacion")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @GetMapping("/citas/historial/{dni}")
    public ResponseEntity<List<Cita>> obtenerHistorialPorDNI(@PathVariable("dni") String dni) {
        System.out.println("📥 DNI recibido para historial: " + dni);
        List<Cita> citas = historialService.obtenerCitasPorDni(dni);
        System.out.println("✅ Total citas encontradas: " + citas.size());
        return ResponseEntity.ok(citas);
    }
}
