package com.mycompany.controller;

import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Obtener todos los médicos")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",description = "Lista de médicos obtenida correctamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Medico.class))
            )
        ),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Medico> obtenerTodos() {
        return medicoService.obtenerTodos();
    }

    @Operation(summary = "Agregar un nuevo médico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico agregado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error al agregar médico")
    })
    @PostMapping
    public Medico agregarMedico(@RequestBody Medico medico) {
        return medicoService.agregarMedico(medico);
    }

    @Operation(summary = "Actualizar los datos de un médico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error al actualizar médico")
    })
    @PutMapping("/{id}")
    public Medico actualizarMedico(@PathVariable Long id, @RequestBody Medico medicoActualizado) {
        return medicoService.actualizarMedico(id, medicoActualizado);
    }

    @Operation(summary = "Eliminar un médico por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Médico eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
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
