package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.DTO.ConversacionDTO;

import static org.junit.jupiter.api.Assertions.*;

class ConversacionDTOTest {

    @Test
    void testNoArgConstructorAndSetters() {
        ConversacionDTO dto = new ConversacionDTO();
        dto.setDni("12345678A");
        dto.setNombre("María");

        assertEquals("12345678A", dto.getDni());
        assertEquals("María", dto.getNombre());
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        ConversacionDTO dto = new ConversacionDTO("87654321B", "Carlos");

        assertEquals("87654321B", dto.getDni());
        assertEquals("Carlos", dto.getNombre());
    }
}
