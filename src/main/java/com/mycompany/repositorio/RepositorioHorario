package com.mycompany.repositorio;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RepositorioHorario extends JpaRepository<Horario, Long> {

    List<Horario> findByMedico(Medico medico);

    List<Horario> findByDia(LocalDate dia);

    List<Horario> findByMedicoAndDia(Medico medico, LocalDate dia);

    void deleteByMedico(Medico medico);
}
