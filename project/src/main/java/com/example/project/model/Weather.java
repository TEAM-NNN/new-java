package com.example.project.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "weather_main")
    private String weatherMain;

    // === Getter & Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
}
