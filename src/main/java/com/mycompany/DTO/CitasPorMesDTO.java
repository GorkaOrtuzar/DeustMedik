package com.mycompany.DTO;

public class CitasPorMesDTO {
    private final int year;
    private final int month;
    private final long total;

    public CitasPorMesDTO(int year, int month, long total) {
        this.year = year;
        this.month = month;
        this.total = total;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public long getTotal() { return total; }
}