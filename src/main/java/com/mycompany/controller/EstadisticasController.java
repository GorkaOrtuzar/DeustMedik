package com.mycompany.controller;

import com.mycompany.DTO.CitasPorMesDTO;
import com.mycompany.DTO.MedicoSolicitadoDTO;
import com.mycompany.repositorio.RepositorioEstadisticas;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final RepositorioEstadisticas repo;
    public EstadisticasController(RepositorioEstadisticas repo) {
        this.repo = repo;
    }

    @GetMapping("/citas-por-mes")
    public List<CitasPorMesDTO> citasPorMes() {
        return repo.countCitasByMes();
    }

    @GetMapping("/medicos-mas-solicitados")
    public List<MedicoSolicitadoDTO> medicosMasSolicitados() {
        return repo.countMedicosMasSolicitados();
    }
}