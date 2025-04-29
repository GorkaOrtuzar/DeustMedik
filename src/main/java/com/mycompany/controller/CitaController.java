package com.mycompany.controller;

import com.mycompany.DTO.ModificarCitaDTO;
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

    @PutMapping("/citas")
    public ResponseEntity<?> actualizarCita(@RequestBody ModificarCitaDTO dto) {
        System.out.println("Recibido DTO: " + dto.getId() + " - " + dto.getFechaHora() + " - " + dto.getMotivo());
        return repositorioCita.findById(dto.getId()).map(cita -> {
            cita.setFechaHora(dto.getFechaHora());
            cita.setMotivo(dto.getMotivo());
            repositorioCita.save(cita);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/citas/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable("id") Long id) {
        if (repositorioCita.existsById(id)) {
            repositorioCita.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
