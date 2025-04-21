package com.mycompany.controller;

import com.mycompany.service.HorarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.mycompany.modelo.Horario;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class HorarioController {

private HorarioService horarioService;
    
@Autowired
public HorarioController(HorarioService horarioService) {
    this.horarioService = horarioService;
}


@Operation(
        summary = "Obtener todos los médicos", 
        description = "Recupera una lista de todos los médicos registrados en el sistema",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "OK. Horario obtenido exitosamente",
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = Horario.class)
                )
            ),
            @ApiResponse(
                responseCode = "204", 
                description = "No hay horarios registrados"
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Error interno del servidor"
            )
        }
    )

    @GetMapping("/medico/Horario")
    public ResponseEntity<List<Horario>> obtenerHorariosPorMedico(@RequestParam("id") Long id) {
        List<Horario> horarios = horarioService.obtenerHorariosPorMedico(id);
        return ResponseEntity.ok(horarios);
    }

    //@GetMapping("/medico/Horario/")
    //public List<Horario> obtenerHorariosPorMedico(
    //@Parameter(description = "Nombre del médico a buscar") @RequestParam("Nombre") String nombre) {
        //List<Horario> horarios = horarioService.obtenerHorarioMedico(nombre);  // Obtiene el objeto Medico completo
        //return horarios;  // Llamar al servicio con el nombre del médico
    //}

    @GetMapping("/horarios")
    public String mostrarHorarios() {
        return "horarios";
    }

}
