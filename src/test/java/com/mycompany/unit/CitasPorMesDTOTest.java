package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.DTO.CitasPorMesDTO;

import static org.junit.jupiter.api.Assertions.*;

class CitasPorMesDTOTest {

    @Test
    void testGetters() {
        CitasPorMesDTO dto = new CitasPorMesDTO(2023, 5, 42L);

        assertEquals(2023, dto.getYear());
        assertEquals(5, dto.getMonth());
        assertEquals(42L, dto.getTotal());
    }
}
