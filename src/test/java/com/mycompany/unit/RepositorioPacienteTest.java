package com.mycompany.unit;

import com.mycompany.modelo.Paciente;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.service.RestApiApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = RestApiApplication.class)
class RepositorioPacienteTest {

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @BeforeEach
    void limpiarBaseDeDatos() {
        repositorioPaciente.deleteAll();
    }

    @Test
    @DisplayName("Guardar y buscar paciente por DNI")
    void testFindByDni() {
        Paciente paciente = new Paciente("12345678A", "Ana", "Perez", "ana@example.com", "pass", "", null);
        repositorioPaciente.save(paciente);

        Optional<Paciente> encontrado = repositorioPaciente.findByDni("12345678A");

        assertTrue(encontrado.isPresent());
        assertEquals("Ana", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Buscar paciente por apellido")
    void testFindByApellido() {
        Paciente paciente = new Paciente("87654321B", "Luis", "Ramirez", "luis@example.com", "pass", "", null);
        repositorioPaciente.save(paciente);

        Optional<Paciente> encontrado = repositorioPaciente.findByApellido("Ramirez");

        assertTrue(encontrado.isPresent());
        assertEquals("Luis", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Buscar paciente por correo")
    void testFindByCorreo() {
        Paciente paciente = new Paciente("11223344C", "Sara", "Lopez", "sara@example.com", "pass", "", null);
        repositorioPaciente.save(paciente);

        Optional<Paciente> encontrado = repositorioPaciente.findByCorreo("sara@example.com");

        assertTrue(encontrado.isPresent());
        assertEquals("Sara", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Comprobar existencia de paciente por DNI")
    void testExistsByDni() {
        Paciente paciente = new Paciente("44556677D", "Carlos", "Gomez", "carlos@example.com", "pass", "", null);
        repositorioPaciente.save(paciente);

        boolean existe = repositorioPaciente.existsByDni("44556677D");
        assertTrue(existe);

        boolean noExiste = repositorioPaciente.existsByDni("99999999Z");
        assertFalse(noExiste);
    }
}
