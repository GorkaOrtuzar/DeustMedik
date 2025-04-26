package com.mycompany.service;

import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    private final RepositorioPaciente repositorioPaciente;

    @Autowired
    public RegistroService(RepositorioPaciente repositorioPaciente) {
        this.repositorioPaciente = repositorioPaciente;
    }

    public boolean registrarPaciente(Paciente paciente) {
        if (repositorioPaciente.existsByDni(paciente.getDni())) {
            return false; // Ya existe un paciente con ese DNI
        }
        repositorioPaciente.save(paciente);
        return true;
    }
}
