package com.mycompany.rendimiento;

import com.mycompany.modelo.Cita;
import com.mycompany.repositorio.RepositorioCita;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

//Este @... es lo que se encarga de que no de error el test, sin el el test no sabe como se inicia todo
@SpringBootTest(classes = com.mycompany.service.RestApiApplication.class)
public class RepositorioCitaRendimientoTest {

    @Autowired
    private RepositorioCita repositorioCita;

    @Test
    void testFindAllRendimiento() {
        long inicio = System.currentTimeMillis();

        List<Cita> citas = repositorioCita.findAll();

        long fin = System.currentTimeMillis();
        long duracion = fin - inicio;

        System.out.println("Tiempo de ejecución de findAll(): " + duracion + " ms");

        assertTrue(duracion < 500, "La consulta findAll() debería ejecutarse en menos de 500 ms");
    }
}
