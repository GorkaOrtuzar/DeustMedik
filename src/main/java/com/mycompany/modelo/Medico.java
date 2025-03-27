package com.mycompany.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Schema(description = "Entidad que representa un médico")
@Entity
@Table(name = "Medico")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del médico", example = "1")
    private Long id;

    @Column(name = "DNI")
    @Schema(description = "DNI del médico", example = "12345678A")
    private String dni;

    @Column(name = "Nombre")
    @Schema(description = "Nombre del médico", example = "Laura")
    private String nombre;

    @Column(name = "Apellido")
    @Schema(description = "Apellido del médico", example = "Gómez")
    private String apellido;

    @Column(name = "Especialidad")
    @Schema(description = "Especialidad médica", example = "Cardiología")
    private String especialidad;

    @Column(name = "Contacto")
    @Schema(description = "Teléfono o email de contacto", example = "laura.gomez@hospital.com")
    private String contacto;

    @Column(name = "Disponibilidad")
    @Schema(description = "Si el médico está disponible actualmente", example = "true")
    private boolean disponibilidad;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)
    private List<Horario> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(hidden = true)
    private List<Cita> citas = new ArrayList<>();

    // === Constructores ===

    public Medico() {
        super();
    }

    public Medico(String dni, String nombre, String apellido, String especialidad, String contacto, List<Horario> horarios) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.contacto = contacto;
        this.horarios = horarios != null ? horarios : new ArrayList<>();
    }

    // === Getters y Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public void addHorario(Horario horario) {
        horario.setMedico(this);
        this.horarios.add(horario);
    }

    public void removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setMedico(null);
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
}
