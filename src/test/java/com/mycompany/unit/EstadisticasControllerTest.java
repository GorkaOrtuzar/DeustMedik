package com.mycompany.unit;

import com.mycompany.DTO.CitasPorMesDTO;
import com.mycompany.DTO.MedicoSolicitadoDTO;
import com.mycompany.controller.EstadisticasController;
import com.mycompany.repositorio.RepositorioEstadisticas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstadisticasControllerTest {

    @Mock
    private RepositorioEstadisticas repo;

    @InjectMocks
    private EstadisticasController controller;

    @Test
    void testCitasPorMes() {
        List<CitasPorMesDTO> lista = List.of(new CitasPorMesDTO(0, 0, 0));
        when(repo.countCitasByMes()).thenReturn(lista);

        List<CitasPorMesDTO> result = controller.citasPorMes();

        assertEquals(lista, result);
    }

    @Test
    void testMedicosMasSolicitados() {
        List<MedicoSolicitadoDTO> lista = List.of(new MedicoSolicitadoDTO(null, null, 0));
        when(repo.countMedicosMasSolicitados()).thenReturn(lista);

        List<MedicoSolicitadoDTO> result = controller.medicosMasSolicitados();

        assertEquals(lista, result);
    }
}

