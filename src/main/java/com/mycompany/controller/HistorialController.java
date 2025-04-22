package com.mycompany.controller;

import com.mycompany.DTO.CitaDTO;
import com.mycompany.modelo.Cita;
import com.mycompany.service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors; // Import necesario para Collectors

@RestController
@RequestMapping("/autorizacion")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @GetMapping("/citas/historial/{dni}")
    public ResponseEntity<List<CitaDTO>> obtenerHistorialPorDNI(@PathVariable("dni") String dni) {
        List<CitaDTO> citasDTO = historialService.obtenerCitasPorDni(dni);
        return ResponseEntity.ok(citasDTO);
    }

}
