package com.mycompany.client;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.mycompany.modelo.Medico;

public class HospitalManager {

    private String MEDICO_CONTROLLER_URL_TEMPLATE = "http://%s:%s/api/medicos";
    private final String MEDICO_CONTROLLER_URL;
    private final RestTemplate restTemplate;

    public HospitalManager(String hostname, String port) {
        this.MEDICO_CONTROLLER_URL = String.format(MEDICO_CONTROLLER_URL_TEMPLATE, hostname, port);
        this.restTemplate = new RestTemplate();
    }

    public List<Medico> visualizarTodosMedicos() {
        ResponseEntity<Medico[]> response = restTemplate.getForEntity(MEDICO_CONTROLLER_URL, Medico[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return List.of(response.getBody());
        } else {
            System.out.println("No se pudo obtener la lista de médicos. Código de estado: " + response.getStatusCode());
            return List.of();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java HospitalManager <hostname> <port>");
            System.exit(0);
        }

        String hostname = args[0];
        String port = args[1];

        HospitalManager hospitalManager = new HospitalManager(hostname, port);
        List<Medico> medicos = hospitalManager.visualizarTodosMedicos();

        if (!medicos.isEmpty()) {
            System.out.println("Lista de médicos:");
            for (Medico medico : medicos) {
                System.out.println("ID: " + medico.getId());
                System.out.println("Nombre: " + medico.getNombre());
                System.out.println("Especialidad: " + medico.getEspecialidad());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("No hay médicos disponibles.");
        }
    }
}

