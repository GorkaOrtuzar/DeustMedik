package com.mycompany.service;

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

    public List<Cita> obtenerCitasPorDni(String dni) {
        return repositorioCita.findByPacienteDNIOrderByFechaHoraDesc(dni);
    } 

}