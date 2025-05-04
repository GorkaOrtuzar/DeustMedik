package com.mycompany.rendimiento;

import com.mycompany.modelo.Medico;
import com.mycompany.repositorio.RepositorioCita;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.service.MedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedicoServiceTestRendimientoTest {
    private RepositorioMedico repositorioMedico;
    private RepositorioCita repositorioCita;
    private MedicoService medicoService;

    @BeforeEach
    void setUp() {
        repositorioMedico = mock(RepositorioMedico.class);
        repositorioCita = mock(RepositorioCita.class);
        medicoService = new MedicoService(repositorioMedico, repositorioCita);
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    void testObtenerMedicosGranVolumen() {
        // Simular una lista grande de médicos
        List<Medico> listaMedicos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Medico medico = new Medico();
            medico.setId((long) i);
            medico.setNombre("Nombre" + i);
            medico.setApellido("Apellido" + i);
            listaMedicos.add(medico);
        }

        when(repositorioMedico.findAll()).thenReturn(listaMedicos);

        long startTime = System.currentTimeMillis();
        List<Medico> resultado = medicoService.obtenerMedicos();
        long endTime = System.currentTimeMillis();

        assertEquals(10000, resultado.size());
        long duration = endTime - startTime;
        System.out.println("Tiempo de ejecución para 10.000 médicos: " + duration + "ms");
        assertTrue(duration < 1000, "La obtención de médicos debe ser menor a 1 segundo");
    }

    @Test
    void testBusquedaMasivaRendimiento() {
        Medico medico = new Medico();
        when(repositorioMedico.findByNombreAndApellido(anyString(), anyString())).thenReturn(medico);

        long startTime = System.currentTimeMillis();
        
        // Realizar múltiples búsquedas
        for (int i = 0; i < 1000; i++) {
            medicoService.buscarPorNombreYApellido("Nombre" + i, "Apellido" + i);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Tiempo para 1000 búsquedas: " + duration + "ms");
        assertTrue(duration < 5000, "1000 búsquedas deben completarse en menos de 5 segundos");
    }
}