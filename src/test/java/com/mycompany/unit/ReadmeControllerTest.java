package com.mycompany.unit;

import org.junit.jupiter.api.Test;

import com.mycompany.controller.ReadmeController;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadmeControllerTest {

    private final ReadmeController controller = new ReadmeController();

    @Test
    void testReadmeView() {
        String result = controller.readmeView();
        assertEquals("forward:/readme.html", result);
    }
}

