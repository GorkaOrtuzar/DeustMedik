package com.mycompany.service;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private RepositorioHorario repositorioHorario;

    public Horario guardarHorario(Horario horario) {
        if (horario.getHoraInicio().isAfter(horario.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio no puede ser mayor que la hora de fin.");
        }
    
        List<Horario> horariosExistentes = repositorioHorario.findByMedicoAndDia(horario.getMedico(), horario.getDia());
        for (Horario existente : horariosExistentes) {
            if (!(horario.getHoraFin().isBefore(existente.getHoraInicio()) || 
                  horario.getHoraInicio().isAfter(existente.getHoraFin()))) {
                throw new IllegalArgumentException("El horario se solapa con otro ya existente.");
            }
        }
    
        return repositorioHorario.save(horario);
    }
    
    public List<Horario> obtenerHorariosPorMedico(Medico medico) {
        return repositorioHorario.findByMedico(medico);
    }

    public List<Horario> obtenerHorariosPorFecha(LocalDate dia) {
        return repositorioHorario.findByDia(dia);
    }

    public List<Horario> obtenerHorariosPorMedicoYFecha(Medico medico, LocalDate dia) {
        return repositorioHorario.findByMedicoAndDia(medico, dia);
    }

    public void eliminarHorario(Long id) {
        repositorioHorario.deleteById(id);
    }

    public void eliminarHorariosPorMedico(Medico medico) {
        repositorioHorario.deleteByMedico(medico);
    }

    public Horario actualizarHorario(Long id, Horario nuevoHorario) {
        Horario horarioExistente = repositorioHorario.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        if (nuevoHorario.getHoraInicio().isAfter(nuevoHorario.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio no puede ser mayor que la hora de fin.");
        }
        horarioExistente.setDia(nuevoHorario.getDia());
        horarioExistente.setHoraInicio(nuevoHorario.getHoraInicio());
        horarioExistente.setHoraFin(nuevoHorario.getHoraFin());
        return repositorioHorario.save(horarioExistente);
    }
    
}
