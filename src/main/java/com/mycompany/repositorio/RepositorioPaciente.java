package com.mycompany.repositorio;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Paciente;

import scala.collection.immutable.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RepositorioPaciente extends JpaRepository<Paciente, Long> {
    
    Optional<Paciente> findByDni(String dni);

    Optional<Paciente> findByApellido(String apellido);

    Optional<Paciente> findByCorreo(String correo);

    boolean existsByDni(String dni);

}
