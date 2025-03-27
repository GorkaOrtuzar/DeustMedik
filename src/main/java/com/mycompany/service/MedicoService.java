package com.mycompany.service;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    
    private final RepositorioMedico repositorioMedico;

    @Autowired
    public MedicoService(RepositorioMedico repositorioMedico) {
        this.repositorioMedico = repositorioMedico;
    }

    public List<Medico> obtenerMedicos() {
        return repositorioMedico.findAll();
    }
}