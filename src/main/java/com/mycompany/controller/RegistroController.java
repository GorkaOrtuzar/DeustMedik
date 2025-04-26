package com.mycompany.controller;

import com.mycompany.modelo.Paciente;
import com.mycompany.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autorizacion")
public class RegistroController {

    private final RegistroService registroService;

    @Autowired
    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/Registro")
    public String mostrarFormularioRegistro() {
        return "Registro";
    }

    @PostMapping("/registro")
    @ResponseBody
    public ResponseEntity<?> registrar(@RequestBody Paciente paciente) {
        boolean registrado = registroService.registrarPaciente(paciente);

        if (registrado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("El DNI ya está registrado");
        }
    }
}
