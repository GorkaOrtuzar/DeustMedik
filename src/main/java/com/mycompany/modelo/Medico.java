package com.mycompany.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Nombre;
    private String Apellido;
    private String Especialidad;
    private String Contacto;

    // No-argument constructor
    public Medico() {
    }

    public Medico(String Nombre, String Apellido, String Especialidad, String Contacto) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Especialidad = Especialidad;
        this.Contacto = Contacto;

    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getApellido() {
        return Apellido;
    }
    public void setApellido(String apellido) {
        Apellido = apellido;
    }
    public String getEspecialidad() {
        return Especialidad;
    }
    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }
}
