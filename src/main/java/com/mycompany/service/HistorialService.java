package com.mycompany.service;

import com.mycompany.DTO.CitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialService {

    @Autowired
    private RepositorioCita repositorioCita;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    public List<CitaDTO> obtenerCitasPorDni(String dni) {
        List<Cita> citas = repositorioCita.findByPacienteDNIOrderByFechaHoraDesc(dni);

        return citas.stream().map(cita -> {
            String nombre = cita.getMedico() != null ? cita.getMedico().getNombre() : "undefined";
            String apellido = cita.getMedico() != null ? cita.getMedico().getApellido() : "undefined";
            String especialidad = cita.getMedico() != null ? cita.getMedico().getEspecialidad() : "undefined";

            return new CitaDTO(
                cita.getFechaHora(),
                nombre,
                apellido,
                especialidad,
                cita.getMotivo()
            );
        }).toList();
    }

}