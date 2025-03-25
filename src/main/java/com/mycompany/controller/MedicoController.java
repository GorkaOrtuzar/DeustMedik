package com.mycompany.controller;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.service.MedicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/buscar")
    public String buscarMedicos(Model model) {
        List<Medico> medicos = medicoService.obtenerTodos();
        model.addAttribute("medicos", medicos);
        return "medicos/lista-medicos";
    }
    
    @GetMapping  // Lista de médicos en página web
    public String obtenerTodos(Model model) {
        List<Medico> medicos = medicoService.obtenerTodos();
        model.addAttribute("medicos", medicos);
        return "medicos/lista-medicos";  // Nombre de la vista Thymeleaf
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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/especialidad/{especialidad}")
    public List<Medico> obtenerPorEspecialidad(@PathVariable String especialidad) {
        return medicoService.obtenerPorEspecialidad(especialidad);
    }

    @GetMapping("/disponibles/todos")
    public List<Medico> obtenerTodosLosMedicosDisponibles() {
        return medicoService.obtenerTodosLosMedicosDisponibles();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Medico>> obtenerMedicosDisponibles(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("horaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam("horaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFin) {

        List<Medico> medicosDisponibles = medicoService.obtenerMedicosDisponibles(fecha, horaInicio, horaFin);

        if (medicosDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(medicosDisponibles);
        }
    }

    @PostMapping("/{id}/horarios")
    public ResponseEntity<Horario> asignarHorarioAMedico(@PathVariable Long id, @RequestBody Horario horario) {
        return ResponseEntity.ok(medicoService.asignarHorarioAMedico(id, horario));
    }

    @GetMapping("/{id}/horarios")
    public ResponseEntity<List<Horario>> obtenerHorariosPorMedico(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.obtenerHorariosPorMedico(id));
    }

    @GetMapping("/disponibles/cita")
    public ResponseEntity<List<Medico>> obtenerMedicosDisponiblesParaCita(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("hora") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora) {

        List<Medico> medicosDisponibles = medicoService.obtenerMedicosDisponiblesParaCita(fecha, hora);

        if (medicosDisponibles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(medicosDisponibles);
        }
    }

}
