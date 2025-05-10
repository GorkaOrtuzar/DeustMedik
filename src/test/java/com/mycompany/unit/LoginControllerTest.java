package com.mycompany.unit;

import com.mycompany.controller.LoginController;
import com.mycompany.modelo.Paciente;
import com.mycompany.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private PacienteService pacienteService;

    @Mock
    private Model model;

    @InjectMocks
    private LoginController controller;

    @Test
    void testRedirigirRaiz() {
        assertEquals("redirect:/login", controller.redirigirRaiz());
    }

    @Test
    void testRedirigirAlLogin() {
        assertEquals("login", controller.redirigirAlLogin());
    }

    @Test
    void testProcesarLoginCorrecto() {
        Paciente paciente = new Paciente();
        paciente.setContrasenia("1234");
        when(pacienteService.getPacienteByDNI("111A")).thenReturn(paciente);

        String result = controller.procesarLogin("111A", "1234", model);

        assertEquals("inicio", result);
        verify(model).addAttribute("usuario", paciente);
    }

    @Test
    void testProcesarLoginIncorrecto() {
        when(pacienteService.getPacienteByDNI("111A")).thenReturn(null);

        String result = controller.procesarLogin("111A", "1234", model);

        assertEquals("login", result);
        verify(model).addAttribute(eq("error"), contains("incorrectos"));
    }
}
