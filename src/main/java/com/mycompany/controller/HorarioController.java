package com.mycompany.controller;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.service.HorarioService;
import com.mycompany.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private MedicoService medicoService;

    @Operation(summary = "Crear un nuevo horario para un médico")
    @ApiResponse(responseCode = "200", description = "Horario creado correctamente")
    @PostMapping("/{medicoId}")
    public ResponseEntity<Horario> crearHorario(@PathVariable Long medicoId, @RequestBody Horario horario) {
        // Verifica que el médico existe
        Medico medico = medicoService.obtenerPorId(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        horario.setMedico(medico);
        // Se delega en HorarioService la lógica de validación y guardado
        Horario nuevoHorario = horarioService.guardarHorario(horario);
        return ResponseEntity.ok(nuevoHorario);
    }

    @Operation(summary = "Obtener horarios de un médico")
    @ApiResponse(responseCode = "200", description = "Horarios obtenidos correctamente")
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Horario>> obtenerHorariosPorMedico(@PathVariable Long medicoId) {
        Medico medico = medicoService.obtenerPorId(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        List<Horario> horarios = horarioService.obtenerHorariosPorMedico(medico);
        return ResponseEntity.ok(horarios);
    }

    @Operation(summary = "Actualizar un horario existente")
    @ApiResponse(responseCode = "200", description = "Horario actualizado correctamente")
    @PutMapping("/{id}")
    public ResponseEntity<Horario> actualizarHorario(@PathVariable Long id, @RequestBody Horario horarioActualizado) {
        // Implementa la lógica de actualización, por ejemplo creando un método en HorarioService
        Horario horarioActualizadoPersistido = horarioService.actualizarHorario(id, horarioActualizado);
        return ResponseEntity.ok(horarioActualizadoPersistido);
    }

    @Operation(summary = "Eliminar un horario")
    @ApiResponse(responseCode = "204", description = "Horario eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarHorario(@PathVariable Long id) {
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}
