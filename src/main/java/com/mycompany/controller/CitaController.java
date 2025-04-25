package com.mycompany.controller;

import com.mycompany.modelo.Cita;
import com.mycompany.repositorio.RepositorioCita;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class CitaController {

    private final RepositorioCita repositorioCita;

    public CitaController(RepositorioCita repositorioCita) {
        this.repositorioCita = repositorioCita;
    }

    @GetMapping
    public List<Cita> obtenerTodasLasCitas() {
        return repositorioCita.findAll();
    }

    @PostMapping("/citas")
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        Cita nueva = repositorioCita.save(cita);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/citas/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable Long id, @RequestBody Cita nuevaCita) {
        return repositorioCita.findById(id).map(cita -> {
            cita.setFechaHora(nuevaCita.getFechaHora());
            cita.setMotivo(nuevaCita.getMotivo());
            repositorioCita.save(cita);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }


}
