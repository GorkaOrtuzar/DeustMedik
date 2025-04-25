package com.mycompany.DTO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ModificarCitaDTO {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHora;
    private String motivo;

    public ModificarCitaDTO() {
    }

    public ModificarCitaDTO(Long id, LocalDateTime fechaHora, String motivo) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
