package com.mycompany.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.mycompany.DTO.CredencialesDTO;
import com.mycompany.DTO.CredencialesMedicoDTO;
import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Operation(
        summary = "Obtener disponibilidad de un médico", 
        description = "Recupera los horarios disponibles de un médico para una fecha específica",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Horarios disponibles obtenidos exitosamente",
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = LocalDateTime.class)
                )
            ),
            @ApiResponse(
                responseCode = "200", 
                description = "No hay horarios disponibles para el médico en la fecha especificada"
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Médico no encontrado"
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Error interno del servidor"
            )
        }
    )
    @PostMapping("/medico/disponibilidad")
    public ResponseEntity<List<LocalDateTime>> obtenerDisponibilidadMedico(
        @RequestBody CredencialesMedicoDTO credentials){
    
        Medico medico = servicioMedico.buscarPorNombreYApellido(credentials.getNombre(),credentials.getApelliddo());

        
        List<LocalDateTime> disponibilidad = servicioMedico.obtenerDisponibilidadMedico(medico.getId());
        
        if (disponibilidad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(disponibilidad, HttpStatus.OK);
    }

    @GetMapping("/medico/Horario")
    public ResponseEntity<?> obtenerHorarioPorNombreYApellido(@RequestParam("Nombre") String nombre,
                                                            @RequestParam("Apellido") String apellido) {
        Medico medico = servicioMedico.buscarPorNombreYApellido(nombre, apellido);

        if (medico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(servicioMedico.obtenerDisponibilidadMedico(medico.getId()));
    }

    @GetMapping("/buscar-medicos")
    public String mostrarPaginaBusqueda() {
        return "buscar-medicos";
    }


}