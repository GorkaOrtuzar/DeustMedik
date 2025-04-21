package com.mycompany.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioHorario;
import com.mycompany.repositorio.RepositorioMedico;


@Service
public class HorarioService {
    private  RepositorioHorario repositorioHorario;
    private  RepositorioMedico repositorioMedico;

    public List<Horario> obtenerHorarioMedico(String nombre) {
        Optional<Medico> medicoOptional = repositorioMedico.findByNombre(nombre);
        if (medicoOptional.isEmpty()) {
            return List.of();  // Retorna una lista vacía si no se encuentra el médico
        }
        Medico medico = medicoOptional.get();  // Obtiene el objeto Medico completo
        return repositorioHorario.findByMedico(medico);  // Busca los horarios usando el objeto Medico completo
    }

    public List<Horario> obtenerHorariosPorMedico(Long id) {
        Optional<Medico> medicoOptional = repositorioMedico.findById(id);
        if (medicoOptional.isEmpty()) {
            return List.of();
        }
        Medico medico = medicoOptional.get();
        return repositorioHorario.findByMedico(medico);
    }
}
