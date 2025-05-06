package com.mycompany.DTO;

public class MedicoSolicitadoDTO {
    private final String nombre;
    private final String apellido;
    private final long totalCitas;

    public MedicoSolicitadoDTO(String nombre, String apellido, long totalCitas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalCitas = totalCitas;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public long getTotalCitas() { return totalCitas; }
}