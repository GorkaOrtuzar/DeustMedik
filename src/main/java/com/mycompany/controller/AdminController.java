package com.mycompany.controller;

import com.mycompany.modelo.Medico;
import com.mycompany.modelo.Paciente;
import com.mycompany.modelo.Cita;
import com.mycompany.repositorio.RepositorioMedico;
import com.mycompany.repositorio.RepositorioPaciente;
import com.mycompany.repositorio.RepositorioCita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private RepositorioMedico repositorioMedico;

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @Autowired
    private RepositorioCita repositorioCita;

    @GetMapping("/admBD")
    public String mostrarBaseDeDatos(Model model) {
        List<Medico> medicos = repositorioMedico.findAll();
        List<Paciente> pacientes = repositorioPaciente.findAll();
        List<Cita> citas = repositorioCita.findAll();

        model.addAttribute("medicos", medicos);
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("citas", citas);

        return "admBD";
    }
}
