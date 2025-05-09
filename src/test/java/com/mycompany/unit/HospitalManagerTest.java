package com.mycompany.unit;

import com.mycompany.cliente.HospitalManager;
import com.mycompany.modelo.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HospitalManagerTest {

    private HospitalManager manager;
    private RestTemplate mockRestTemplate;
    private final String baseUrl = "http://hostname:port/api/medicos";

    @BeforeEach
    void setUp() {
        manager = new HospitalManager("hostname", "port");

        mockRestTemplate = mock(RestTemplate.class);
        ReflectionTestUtils.setField(manager, "restTemplate", mockRestTemplate);
    }

    @Test
    void mostrarInicio_returnsInicioView() {
        String view = manager.mostrarInicio();
        assertEquals("inicio", view);
    }

    @Test
    void getAllMedicos_onApiSuccess_addsListToModel() {
        Medico m1 = new Medico(); m1.setId(1L); m1.setNombre("Ana");
        Medico m2 = new Medico(); m2.setId(2L); m2.setNombre("Luis");
        Medico[] array = new Medico[]{ m1, m2 };

        when(mockRestTemplate.getForEntity(eq(baseUrl), eq(Medico[].class)))
            .thenReturn(ResponseEntity.ok(array));

        Model model = new ExtendedModelMap();
        String view = manager.getAllMedicos(model);

        assertEquals("medicos", view);
        assertTrue(model.containsAttribute("medicos"));

        @SuppressWarnings("unchecked")
        List<Medico> list = (List<Medico>) model.getAttribute("medicos");
        assertEquals(2, list.size());
        assertEquals("Ana", list.get(0).getNombre());
        assertEquals("Luis", list.get(1).getNombre());
    }

    @Test
    void getAllMedicos_onNon2xxStatus_addsEmptyList() {
        Medico[] emptyArray = new Medico[]{};
        ResponseEntity<Medico[]> bad = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emptyArray);

        when(mockRestTemplate.getForEntity(eq(baseUrl), eq(Medico[].class)))
            .thenReturn(bad);

        Model model = new ExtendedModelMap();
        manager.getAllMedicos(model);

        assertTrue(model.containsAttribute("medicos"));
        @SuppressWarnings("unchecked")
        List<Medico> list = (List<Medico>) model.getAttribute("medicos");
        assertTrue(list.isEmpty());
    }

    @Test
    void getAllMedicos_onException_addsEmptyList() {
        when(mockRestTemplate.getForEntity(eq(baseUrl), eq(Medico[].class)))
            .thenThrow(new RestClientException("API down"));

        Model model = new ExtendedModelMap();
        manager.getAllMedicos(model);

        assertTrue(model.containsAttribute("medicos"));
        @SuppressWarnings("unchecked")
        List<Medico> list = (List<Medico>) model.getAttribute("medicos");
        assertTrue(list.isEmpty());
    }

    @Test
    void mostrarHorarios_onConnectionFailure_addsEmptyListAndReturnsView() {
        Model model = new ExtendedModelMap();
        String view = manager.mostrarHorarios(model);

        assertEquals("horarios", view);
        assertTrue(model.containsAttribute("horarios"));
        @SuppressWarnings("unchecked")
        List<?> list = (List<?>) model.getAttribute("horarios");
        assertTrue(list.isEmpty());
    }
}
