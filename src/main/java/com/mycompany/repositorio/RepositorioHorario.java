package com.mycompany.repositorio;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositorioHorario extends JpaRepository<Horario, Long> {
        List<Horario> findByMedico(Medico medico);  

}
