package com.example.project.dto;

import java.time.LocalDate;

public class WeatherDto {
    private LocalDate date;
    private float temp_max;
    private float temp_min;
    private String weather_main;
    private int humidity;
    private float wind_speed;

    // getter/setter
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public float getTemp_max() { return temp_max; }
    public void setTemp_max(float temp_max) { this.temp_max = temp_max; }

    public float getTemp_min() { return temp_min; }
    public void setTemp_min(float temp_min) { this.temp_min = temp_min; }

    public String getWeather_main() { return weather_main; }
    public void setWeather_main(String weather_main) { this.weather_main = weather_main; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public float getWind_speed() { return wind_speed; }
    public void setWind_speed(float wind_speed) { this.wind_speed = wind_speed; }
}
