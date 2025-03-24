package com.mycompany.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Medico")
public class Medico extends Persona {
    
    private String especialidad;
    private String contacto;

    // No-argument constructor
    public Medico() {
        super();
    }

    public Medico(String dni, String nombre, String apellido, String telefono, String especialidad, String contacto) {
        super(dni, nombre, apellido, telefono);
        this.especialidad = especialidad;
        this.contacto = contacto;
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
}
