package com.mycompany.unit;

import com.mycompany.modelo.Horario;
import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioHorario;
import com.mycompany.repositorio.RepositorioMedico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import com.mycompany.service.RestApiApplication;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = RestApiApplication.class)
class RepositorioHorarioTest {

    @Autowired
    private RepositorioHorario repositorioHorario;

    @Autowired
    private RepositorioMedico repositorioMedico;

    @BeforeEach
    void limpiarBaseDeDatos() {
        repositorioHorario.deleteAll();
        repositorioMedico.deleteAll();
    }

    @Test
    @DisplayName("Guardar y buscar horarios por médico")
    void testFindByMedico() {
        Medico medico = new Medico("12345678Z", "Laura", "Sanchez", "Dermatología", "laura@example.com", null);
        repositorioMedico.save(medico);

        Horario horario1 = new Horario("Lunes", LocalTime.of(9, 0), LocalTime.of(13, 0), medico);
        Horario horario2 = new Horario("Martes", LocalTime.of(10, 0), LocalTime.of(14, 0), medico);

        repositorioHorario.save(horario1);
        repositorioHorario.save(horario2);

        List<Horario> horarios = repositorioHorario.findByMedico(medico);

        assertEquals(2, horarios.size());
        assertEquals("Lunes", horarios.get(0).getDia());
        assertEquals("Martes", horarios.get(1).getDia());
    }
}
