package com.mycompany.controller;

import com.mycompany.modelo.Cita;
import com.mycompany.repositorio.RepositorioCita;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final RepositorioCita repositorioCita;

    public CitaController(RepositorioCita repositorioCita) {
        this.repositorioCita = repositorioCita;
    }

    @GetMapping
    public List<Cita> obtenerTodasLasCitas() {
        return repositorioCita.findAll();
    }
}
