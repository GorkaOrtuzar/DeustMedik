package com.mycompany.service;

import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Horario;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.repositorio.RepositorioHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private RepositorioMedico repositorioMedico;

    @Autowired
    private RepositorioHorario repositorioHorario;

    public List<Medico> obtenerTodos() {
   
            List<Medico> medicos = repositorioMedico.findAll();
            if (medicos.isEmpty()) {
                System.err.println("No hay médicos disponibles en este momento.");
                return medicos;
            } else {
                System.out.println("Se han encontrado " + medicos.size() + " médicos.");
                return medicos;

            }
    }
    public Optional<Medico> obtenerPorId(Long id) {
        return repositorioMedico.findById(id);
    }

    public Medico agregarMedico(Medico medico) {
        return repositorioMedico.save(medico);
    }

    public Medico actualizarMedico(Long id, Medico medicoActualizado) {
        return repositorioMedico.findById(id)
                .map(m -> {
                    m.setDni(medicoActualizado.getDni());
                    m.setNombre(medicoActualizado.getNombre());
                    m.setApellido(medicoActualizado.getApellido());
                    m.setEspecialidad(medicoActualizado.getEspecialidad());
                    m.setContacto(medicoActualizado.getContacto());
                    return repositorioMedico.save(m);
                })
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public void eliminarMedico(Long id) {
        if (repositorioHorario.findByMedico(repositorioMedico.findById(id).orElseThrow(
                () -> new RuntimeException("Médico no encontrado"))).isEmpty()) {
            repositorioMedico.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar el médico porque tiene horarios asignados.");
        }
    }

    

    public Horario asignarHorarioAMedico(Long medicoId, Horario horario) {
        Medico medico = repositorioMedico.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        horario.setMedico(medico);
        return repositorioHorario.save(horario);
    }

    public List<Horario> obtenerHorariosPorMedico(Long medicoId) {
        Medico medico = medicoService.obtenerPorId(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        return repositorioHorario.findByMedico(medico);
    }
    

   
}
