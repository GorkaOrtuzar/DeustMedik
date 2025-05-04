package com.mycompany.repositorio;

import com.mycompany.modelo.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioNotificacion extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByUsuarioDniAndFechaBetweenOrderByFechaDesc(
        String usuarioDni, LocalDateTime desde, LocalDateTime hasta);

    List<Notificacion> findByUsuarioDniOrderByFechaDesc(String usuarioDni);
}