package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.DTO.CredencialesDTO;

import static org.junit.jupiter.api.Assertions.*;

class CredencialesDTOTest {

    @Test
    void testSettersAndGetters() {
        CredencialesDTO dto = new CredencialesDTO();
        dto.setDni("11112222C");
        dto.setContrasenia("secreto123");

        assertEquals("11112222C", dto.getDni());
        assertEquals("secreto123", dto.getContrasenia());
    }
}

