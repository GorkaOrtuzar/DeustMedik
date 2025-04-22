package com.mycompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/proximamente")
    public String mostrarProximamente() {
        return "proximamente"; // este debe coincidir con el nombre del archivo .html en templates (sin extensión)
    }
}
