package com.mycompany.controller;

import com.mycompany.modelo.Paciente;
import com.mycompany.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/")
    public String redirigirRaiz() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String redirigirAlLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("dni") String dni,
                                 @RequestParam("contraseña") String contraseña,
                                 Model model) {

        Paciente paciente = pacienteService.getPacienteByDNI(dni);

        if (paciente != null && paciente.getContrasenia().equals(contraseña)) {
            model.addAttribute("usuario", paciente);
            return "inicio"; // Carga inicio.html
        }

        model.addAttribute("error", "DNI o contraseña incorrectos");
        return "login";
    }

}
