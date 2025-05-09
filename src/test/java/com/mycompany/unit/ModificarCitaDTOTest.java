package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.DTO.ModificarCitaDTO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ModificarCitaDTOTest {

    @Test
    void testNoArgConstructorAndSetters() {
        ModificarCitaDTO dto = new ModificarCitaDTO();
        LocalDateTime fecha = LocalDateTime.of(2024, 2, 20, 11, 15);

        dto.setId(99L);
        dto.setFechaHora(fecha);
        dto.setMotivo("Cambio horario");

        assertEquals(99L, dto.getId());
        assertEquals(fecha, dto.getFechaHora());
        assertEquals("Cambio horario", dto.getMotivo());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime fecha = LocalDateTime.of(2024, 3, 10, 16, 0);
        ModificarCitaDTO dto = new ModificarCitaDTO(123L, fecha, "Consulta urgente");

        assertEquals(123L, dto.getId());
        assertEquals(fecha, dto.getFechaHora());
        assertEquals("Consulta urgente", dto.getMotivo());
    }
}

