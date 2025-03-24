package com.mycompany.controller;

import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private RepositorioMedico repositorioMedico;

    @GetMapping
    public List<Medico> obtenerTodos() {
        return repositorioMedico.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Medico> obtenerPorId(@PathVariable Long id) {
        return repositorioMedico.findById(id);
    }

    @PostMapping
    public Medico agregarMedico(@RequestBody Medico medico) {
        return repositorioMedico.save(medico);
    }

    @PutMapping("/{id}")
    public Medico actualizarMedico(@PathVariable Long id, @RequestBody Medico medicoActualizado) {
        return repositorioMedico.findById(id)
                .map(m -> {
                    m.setDni(medicoActualizado.getDni());
                    m.setNombre(medicoActualizado.getNombre());
                    m.setApellido(medicoActualizado.getApellido());
                    m.setTelefono(medicoActualizado.getTelefono());
                    m.setEspecialidad(medicoActualizado.getEspecialidad());
                    m.setContacto(medicoActualizado.getContacto());
                    return repositorioMedico.save(m);
                })
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void eliminarMedico(@PathVariable Long id) {
        repositorioMedico.deleteById(id);
    }
}
