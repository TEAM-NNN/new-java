package com.example.project.model;

import java.time.LocalDate;

public class DailyStatusDTO {

    private LocalDate date;
    private int totalSales;
    private String weatherMain;

    // === Constructor ===
    public DailyStatusDTO(LocalDate date, int totalSales, String weatherMain) {
        this.date = date;
        this.totalSales = totalSales;
        this.weatherMain = weatherMain;
    }

    // === Getter & Setter ===

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
}
