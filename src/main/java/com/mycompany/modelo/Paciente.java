package com.mycompany.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DNI")
    private String dni;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Correo")
    private String correo;
    @Column(name = "Contraseña")
    private String contrasenia;
    @Column(name = "Historial")
    private String historial;
    
    @OneToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    // No-argument constructor
    public Paciente(){

    }

    public Paciente(String dni, String nombre,String apellido, String correo,
    String contrasenia, String historial, Medico medico ){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.historial = historial;
        this.medico = medico;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getHistorial() {
        return historial;
    }

    public Medico getMedico() {
        return medico;
    }

}
