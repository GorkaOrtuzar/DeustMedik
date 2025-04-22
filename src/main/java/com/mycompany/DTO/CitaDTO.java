package com.mycompany.DTO;

import com.mycompany.modelo.Cita;
import com.mycompany.modelo.Medico;

import java.time.LocalDateTime;

public class CitaDTO {
    private LocalDateTime fecha;
    private String medicoNombre;
    private String medicoApellido;
    private String especialidad;
    private String motivo;

    // Constructor que recibe un Cita
    public CitaDTO(Cita cita) {
        this.fecha = cita.getFechaHora();
        this.motivo = cita.getMotivo();

        Medico medico = cita.getMedico();
        if (medico != null) {
            this.medicoNombre = medico.getNombre();
            this.medicoApellido = medico.getApellido();
            this.especialidad = medico.getEspecialidad();
        } else {
            this.medicoNombre = "Desconocido";
            this.medicoApellido = "";
            this.especialidad = "Desconocida";
        }
    }

    // Constructor alternativo opcional (por si lo quieres usar directamente también)
    public CitaDTO(LocalDateTime fecha, String medicoNombre, String medicoApellido, String especialidad, String motivo) {
        this.fecha = fecha;
        this.medicoNombre = medicoNombre;
        this.medicoApellido = medicoApellido;
        this.especialidad = especialidad;
        this.motivo = motivo;
    }

    // Getters
    public LocalDateTime getFecha() { return fecha; }
    public String getMedicoNombre() { return medicoNombre; }
    public String getMedicoApellido() { return medicoApellido; }
    public String getEspecialidad() { return especialidad; }
    public String getMotivo() { return motivo; }
}

