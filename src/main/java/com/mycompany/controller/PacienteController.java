package com.mycompany.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.service.PacienteService;
import com.mycompany.DTO.CredencialesDTO;
import com.mycompany.modelo.Cita;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping({"/autorizacion"})
@Tag(
   name = "Controller de autorización",
   description = "Operaciones de login y autorización para pacientes"
)
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(
       summary = "Login en el sistema",
       description = "Permite a un paciente iniciar sesión proporcionando su DNI y contraseña. Devuelve un token si es exitoso.",
       responses = {@ApiResponse(
                 responseCode = "200",
                 description = "OK: Inicio de sesión exitoso, devuelve un token"
                 ), @ApiResponse(
                 responseCode = "401",
                 description = "Unauthorized: Credenciales inválidas, inicio de sesión fallido"
                 )}
    )
    @PostMapping({"/login"})
    public ResponseEntity<String> login(@RequestBody CredencialesDTO credentials) {
       Optional<String> token = this.pacienteService.login(credentials.getDni(), credentials.getContrasenia());
       return token.map(t -> 
          new ResponseEntity<>(t, HttpStatus.OK)
       ).orElseGet(() -> 
          new ResponseEntity<>(HttpStatus.UNAUTHORIZED)
       );
    }

}