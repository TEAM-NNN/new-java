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

    @Column(name = "temp_max")
    private Float tempMax;

    @Column(name = "temp_min")
    private Float tempMin;

    @Column(name = "weather_main")
    private String weatherMain;

    private Integer humidity;

    @Column(name = "wind_speed")
    private Float windSpeed;

    // === Getter & Setter ===

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Float getTempMax() { return tempMax; }
    public void setTempMax(Float tempMax) { this.tempMax = tempMax; }

    public Float getTempMin() { return tempMin; }
    public void setTempMin(Float tempMin) { this.tempMin = tempMin; }

    public String getWeatherMain() { return weatherMain; }
    public void setWeatherMain(String weatherMain) { this.weatherMain = weatherMain; }

    public Integer getHumidity() { return humidity; }
    public void setHumidity(Integer humidity) { this.humidity = humidity; }

    public Float getWindSpeed() { return windSpeed; }
    public void setWindSpeed(Float windSpeed) { this.windSpeed = windSpeed; }
}
