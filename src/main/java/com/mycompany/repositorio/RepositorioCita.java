package com.mycompany.repositorio;

import com.mycompany.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositorioCita extends JpaRepository<Cita, Long> {
    List<Cita> findByMedicoId(Long medicoId);
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}
