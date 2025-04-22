package com.mycompany.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    @Column(name = "DNI")
    private String pacienteDNI;
    @Column(name = "Paciente")
    private String pacienteNombre;
    @Column(name = "Apellido")
    private String pacienteApellido;
    @Column(name = "Fecha")
    private LocalDateTime fechaHora;
    @Column(name = "Motivo")
    private String motivo;

    public Cita() {
    }

    public Cita(Medico medico,String pacienteDNI ,String pacienteNombre, String pacienteApellido, LocalDateTime fechaHora, String motivo) {
        this.medico = medico;
        this.pacienteDNI = pacienteDNI;
        this.pacienteNombre = pacienteNombre;
        this.pacienteApellido = pacienteApellido;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    public String getPacienteDNI() {
        return pacienteDNI;
    }
    public void setPacienteDNI(String pacienteDNI) {
        this.pacienteDNI = pacienteDNI;
    }
    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getPacienteApellido() {
        return pacienteApellido;
    }

    public void setPacienteApellido(String pacienteApellido) {
        this.pacienteApellido = pacienteApellido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
}
