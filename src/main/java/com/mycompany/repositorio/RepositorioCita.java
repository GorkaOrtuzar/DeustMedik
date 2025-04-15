package com.mycompany.repositorio;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositorioCita extends JpaRepository<Cita, Long> {
    List<Cita> findByMedicoId(Long medicoId);
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT c.medico FROM Cita c WHERE c.fechaHora = :fechaHora")
    List<Medico> findMedicosConCitaEnFecha(@Param("fechaHora") LocalDateTime fechaHora);
    List<Cita> findByPacienteDNIOrderByFechaHoraDesc(String pacienteDNI);
}
