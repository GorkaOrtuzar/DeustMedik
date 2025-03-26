package com.mycompany.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/deustMedik")
public class HospitalManager {

@GetMapping("/medicos")  
public String obtenerTodos(Model model) {
    return "medicos";
}
}