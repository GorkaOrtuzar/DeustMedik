package com.mycompany.repositorio;

import com.mycompany.modelo.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMedico extends JpaRepository<Medico, Long> {
    
}
