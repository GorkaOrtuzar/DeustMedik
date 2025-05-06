package com.mycompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstadisticasViewController {

    @GetMapping("/Estadisticas")
    public String mostrarEstadisticas() {
        return "forward:/estadisticas.html";
    }
}