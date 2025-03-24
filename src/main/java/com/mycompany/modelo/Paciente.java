package com.mycompany.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasenia;
    private String diagnostico;
    private Medico medico;

    // No-argument constructor
    public Paciente(){

    }

    public Paciente(String dni, String nombre,String apellido, String correo,
    String contrasenia, String diagnostico, Medico medico ){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.diagnostico = diagnostico;
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

    public String getDiagnostico() {
        return diagnostico;
    }

    public Medico getMedico() {
        return medico;
    }

}
