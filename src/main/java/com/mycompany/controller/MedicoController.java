package com.mycompany.controller;

import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public List<Medico> obtenerTodos() {
        return medicoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Medico> obtenerPorId(@PathVariable Long id) {
        return medicoService.obtenerPorId(id);
    }

    @PostMapping
    public Medico agregarMedico(@RequestBody Medico medico) {
        return medicoService.agregarMedico(medico);
    }

    @PutMapping("/{id}")
    public Medico actualizarMedico(@PathVariable Long id, @RequestBody Medico medicoActualizado) {
        return medicoService.actualizarMedico(id, medicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMedico(@PathVariable Long id) {
        try {
            medicoService.eliminarMedico(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
