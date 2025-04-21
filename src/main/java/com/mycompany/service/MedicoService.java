package com.mycompany.service;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    
    private final RepositorioMedico repositorioMedico;
    private final RepositorioCita repositorioCita;

    @Autowired
    public MedicoService(RepositorioMedico repositorioMedico, RepositorioCita repositorioCita) {
        this.repositorioMedico = repositorioMedico;
        this.repositorioCita = repositorioCita;
    }

    public List<Medico> obtenerMedicos() {
        return repositorioMedico.findAll();
    }

    public List<LocalDateTime> obtenerDisponibilidadMedico(Long medicoId) {
        // Obtener el médico por su ID
        Optional<Medico> medicoOptional = repositorioMedico.findById(medicoId);
        
        if (medicoOptional.isEmpty()) {
            System.out.println("Médico no encontrado con ID: " + medicoId);
            return Collections.emptyList();
        }
        
        Medico medico = medicoOptional.get();
        
        // Obtener horario laboral del médico (esto dependerá de cómo tengas modelado el horario del médico)
        // Por ejemplo, asumiendo que el médico trabaja de 9:00 a 17:00
        //Cambiar esto por el horario real del médico
        LocalTime horaInicio = LocalTime.of(9, 0);
        LocalTime horaFin = LocalTime.of(17, 0);
        
        // Duración de cada cita (15 minutos)
        int duracionCitaMinutos = 15;
        
        // Fecha actual para crear los slots
        LocalDate fechaActual = LocalDate.now();
        
        // Crear lista de todos los slots posibles para un día típico
        List<LocalDateTime> slotsDisponibles = new ArrayList<>();
        LocalDateTime slotActual = LocalDateTime.of(fechaActual, horaInicio);
        LocalDateTime finJornada = LocalDateTime.of(fechaActual, horaFin);
        
        while (slotActual.isBefore(finJornada)) {
            slotsDisponibles.add(slotActual);
            slotActual = slotActual.plusMinutes(duracionCitaMinutos);
        }
        
        return slotsDisponibles;
    }

    public Medico buscarPorNombreYApellido(String nombre, String apellido) {
        return repositorioMedico.findByNombreAndApellido(nombre, apellido);
    }
}