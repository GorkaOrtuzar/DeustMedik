package com.mycompany.repositorio;

import com.mycompany.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RepositorioMedico extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidad(String especialidad);

    List<Medico> findByDisponibilidadTrue();

    Optional<Medico> findByCorreo(String correo);

    boolean existsByIdAndCitasIsNotEmpty(Long id);
}
