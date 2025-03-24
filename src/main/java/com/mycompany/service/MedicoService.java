package com.mycompany.service;

import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private RepositorioMedico repositorioMedico;

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
                    m.setTelefono(medicoActualizado.getTelefono());
                    m.setEspecialidad(medicoActualizado.getEspecialidad());
                    m.setContacto(medicoActualizado.getContacto());
                    return repositorioMedico.save(m);
                })
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    }

    public void eliminarMedico(Long id) {
        repositorioMedico.deleteById(id);
    }
}
