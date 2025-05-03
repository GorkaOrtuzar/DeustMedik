package com.mycompany.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remitenteDni;
    private String destinatarioDni;
    
    @Column(columnDefinition = "TEXT")
    private String contenido;

    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public String getRemitenteDni() {
        return remitenteDni;
    }

    public void setRemitenteDni(String remitenteDni) {
        this.remitenteDni = remitenteDni;
    }

    public String getDestinatarioDni() {
        return destinatarioDni;
    }

    public void setDestinatarioDni(String destinatarioDni) {
        this.destinatarioDni = destinatarioDni;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
