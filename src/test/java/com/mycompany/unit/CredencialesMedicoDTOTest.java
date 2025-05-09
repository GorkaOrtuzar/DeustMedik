package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.DTO.CredencialesMedicoDTO;

import static org.junit.jupiter.api.Assertions.*;

class CredencialesMedicoDTOTest {

    @Test
    void testSettersAndGetters() {
        CredencialesMedicoDTO dto = new CredencialesMedicoDTO();
        dto.setNombre("Lucía");
        dto.setApelliddo("Ramírez");

        assertEquals("Lucía", dto.getNombre());
        assertEquals("Ramírez", dto.getApelliddo());
    }
}
