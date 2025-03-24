package com.mycompany.service;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private RepositorioHorario repositorioHorario;

    public Horario guardarHorario(Horario horario) {
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
}
