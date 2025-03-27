package com.mycompany.controller;
import com.mycompany.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mycompany.modelo.Medico;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    
    private final MedicoService servicioMedico;

    // Constructor con inyección de dependencias
    @Autowired
    public MedicoController(MedicoService servicioMedico) {
        this.servicioMedico = servicioMedico;
    }

    @GetMapping
    public ResponseEntity<List<Medico>> obtenerTodos() {
        List<Medico> medicos = servicioMedico.obtenerMedicos();
        return ResponseEntity.ok(medicos);
    }
}