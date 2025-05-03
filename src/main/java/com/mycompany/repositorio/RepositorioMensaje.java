package com.mycompany.repositorio;

import com.mycompany.modelo.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RepositorioMensaje extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByRemitenteDniAndDestinatarioDniOrRemitenteDniAndDestinatarioDniOrderByFecha(
            String remitenteDni1, String destinatarioDni1,
            String remitenteDni2, String destinatarioDni2
    );

    @Query("""
      SELECT CASE
        WHEN m.remitenteDni = :dni THEN m.destinatarioDni
        ELSE m.remitenteDni
      END
      FROM Mensaje m
      WHERE m.remitenteDni = :dni OR m.destinatarioDni = :dni
      GROUP BY
        CASE
          WHEN m.remitenteDni = :dni THEN m.destinatarioDni
          ELSE m.remitenteDni
        END
    """)
    List<String> findConversacionPartners(@Param("dni") String dni);
}