package com.mycompany.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Operaciones relacionadas con médicos")
public class MedicoController {
    
    private final MedicoService servicioMedico;

    @Autowired
    public MedicoController(MedicoService servicioMedico) {
        this.servicioMedico = servicioMedico;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los médicos", 
        description = "Recupera una lista de todos los médicos registrados en el sistema",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Lista de médicos obtenida exitosamente",
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = Medico.class)
                )
            ),
            @ApiResponse(
                responseCode = "204", 
                description = "No hay médicos registrados"
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Error interno del servidor"
            )
        }
    )
    public ResponseEntity<List<Medico>> obtenerTodos() {
        List<Medico> medicos = servicioMedico.obtenerMedicos();
        
        if (medicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(medicos);
    }
}