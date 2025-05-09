package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.DTO.MedicoSolicitadoDTO;

import static org.junit.jupiter.api.Assertions.*;

class MedicoSolicitadoDTOTest {

    @Test
    void testGetters() {
        MedicoSolicitadoDTO dto = new MedicoSolicitadoDTO("Alberto", "Muñoz", 17L);

        assertEquals("Alberto", dto.getNombre());
        assertEquals("Muñoz", dto.getApellido());
        assertEquals(17L, dto.getTotalCitas());
    }
}

