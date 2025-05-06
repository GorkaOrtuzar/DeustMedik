package com.mycompany.repositorio;

import com.mycompany.DTO.CitasPorMesDTO;
import com.mycompany.DTO.MedicoSolicitadoDTO;
import com.mycompany.modelo.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositorioEstadisticas extends JpaRepository<Cita, Long> {

    @Query("""
      SELECT new com.mycompany.DTO.CitasPorMesDTO(
        FUNCTION('YEAR', c.fechaHora),
        FUNCTION('MONTH', c.fechaHora),
        COUNT(c)
      )
      FROM Cita c
      GROUP BY FUNCTION('YEAR', c.fechaHora), FUNCTION('MONTH', c.fechaHora)
      ORDER BY FUNCTION('YEAR', c.fechaHora), FUNCTION('MONTH', c.fechaHora)
      """
    )
    List<CitasPorMesDTO> countCitasByMes();

    @Query("""
      SELECT new com.mycompany.DTO.MedicoSolicitadoDTO(
        c.medico.nombre,
        c.medico.apellido,
        COUNT(c)
      )
      FROM Cita c
      GROUP BY c.medico.id, c.medico.nombre, c.medico.apellido
      ORDER BY COUNT(c) DESC
      """
    )
    List<MedicoSolicitadoDTO> countMedicosMasSolicitados();
}