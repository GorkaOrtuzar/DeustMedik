package com.mycompany.service;

import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Horario;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private RepositorioMedico repositorioMedico;

    @Autowired
    private RepositorioHorario repositorioHorario;

    @Autowired
    private RepositorioCita repositorioCita;

    public List<Medico> obtenerTodos() {
        return repositorioMedico.findAll();
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

    public List<Medico> obtenerPorEspecialidad(String especialidad) {
        return repositorioMedico.findByEspecialidad(especialidad);
    }

    public List<Medico> obtenerTodosLosMedicosDisponibles() {
        return repositorioMedico.findByDisponibilidadTrue();
    }

    public Horario asignarHorarioAMedico(Long medicoId, Horario horario) {
        Medico medico = repositorioMedico.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        horario.setMedico(medico);
        return repositorioHorario.save(horario);
    }

    public List<Horario> obtenerHorariosPorMedico(Long medicoId) {
        Medico medico = repositorioMedico.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        return repositorioHorario.findByMedico(medico);
    }

    public List<Medico> obtenerMedicosDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        return repositorioMedico.findMedicosDisponibles(fecha, horaInicio, horaFin);
    }

    public List<Medico> obtenerMedicosDisponiblesParaCita(LocalDate fecha, LocalTime hora) {
    LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

    List<Medico> medicosConCita = repositorioCita.findMedicosConCitaEnFecha(fechaHora);
    List<Medico> medicosDisponibles = repositorioMedico.findMedicosDisponibles(fecha, hora, hora.plusMinutes(30));
    medicosDisponibles.removeAll(medicosConCita);

    return medicosDisponibles;
}
}
