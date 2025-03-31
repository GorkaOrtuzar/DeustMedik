package com.mycompany.client;

import com.mycompany.modelo.Medico;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class HospitalManager {

    private final String HOSPITAL_CONTROLLER_URL;
    private final RestTemplate restTemplate;

    // Constructor con inyección de propiedades
    public HospitalManager(@Value("${hospital.hostname}") String hostname,
                           @Value("${hospital.port}") String port) {
        this.HOSPITAL_CONTROLLER_URL = "http://" + hostname + ":" + port + "/api/medicos";
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String inicio() {
        return "Inicio"; // para que funcione Inicio.html
    }

    @GetMapping("/medicos")
    public String getAllMedicos(Model model) {
        List<Medico> medicos = getMedicosFromAPI();
        model.addAttribute("medicos", medicos);
        return "medicos";
    }

    private List<Medico> getMedicosFromAPI() {
        try {
            ResponseEntity<Medico[]> response = restTemplate.getForEntity(HOSPITAL_CONTROLLER_URL, Medico[].class);
            return response.getStatusCode().is2xxSuccessful() ? List.of(response.getBody()) : List.of();
        } catch (Exception e) {
            System.out.println("Error en la comunicación con la API: " + e.getMessage());
            return List.of();
        }
    }
}
