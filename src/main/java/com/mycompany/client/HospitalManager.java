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

    // Usa @Value para inyectar las propiedades
    @Value("${hospital.hostname}")
    private String hostname;

    @Value("${hospital.port}")
    private String port;

    private final String HOSPITAL_CONTROLLER_URL;
    private final RestTemplate restTemplate;

    // Constructor sin parámetros, porque ya estamos inyectando las propiedades con @Value
    public HospitalManager() {
        this.HOSPITAL_CONTROLLER_URL = String.format("http://%s:%s/api/medicos", hostname, port);
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/medicos")
    public String getAllMedicos(Model model) {
        // Llama a la API para obtener la lista de médicos
        List<Medico> medicos = getMedicosFromAPI();

        // Pasa la lista de médicos al modelo para Thymeleaf
        model.addAttribute("medicos", medicos);

        // Retorna el nombre de la vista (HTML) que se debe renderizar
        return "medicos";  // Es el nombre del archivo HTML (medicos.html)
    }

    private List<Medico> getMedicosFromAPI() {
        try {
            ResponseEntity<Medico[]> response = restTemplate.getForEntity(HOSPITAL_CONTROLLER_URL, Medico[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return List.of(response.getBody());
            } else {
                System.out.println("Error al obtener los médicos. Código de estado: " + response.getStatusCode());
                return List.of();
            }
        } catch (Exception e) {
            System.out.println("Error en la comunicación con la API: " + e.getMessage());
            return List.of();
        }
    }
}
