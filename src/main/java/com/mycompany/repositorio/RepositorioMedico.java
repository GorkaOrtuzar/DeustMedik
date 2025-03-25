package com.mycompany.repositorio;

import com.mycompany.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RepositorioMedico extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidad(String especialidad);

    List<Medico> findByDisponibilidadTrue();

    Optional<Medico> findByCorreo(String correo);

    boolean existsByIdAndCitasIsNotEmpty(Long id);

    @Query("SELECT DISTINCT m FROM Medico m " +
       "JOIN m.horarios h " +
       "WHERE h.dia = :fecha " +
       "AND h.horaInicio <= :horaInicio " +
       "AND h.horaFin >= :horaFin")
    List<Medico> findMedicosDisponibles(@Param("fecha") LocalDate fecha, 
                                    @Param("horaInicio") LocalTime horaInicio, 
                                    @Param("horaFin") LocalTime horaFin);

}
