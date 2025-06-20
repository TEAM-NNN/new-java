package com.example.project.service;

import com.example.project.dto.WeatherDto;
import com.example.project.model.Weather;
import com.example.project.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class WeatherApiService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Async
    public void fetchAndSaveWeatherForecast() {
        String url = "https://predapi.azurewebsites.net/api/forecast";

        RestTemplate restTemplate = new RestTemplate();

        // WeatherDto[] にマッピング
        WeatherDto[] response = restTemplate.getForObject(url, WeatherDto[].class);
        if (response == null) return;

        List<WeatherDto> forecastList = Arrays.asList(response);

        for (WeatherDto dto : forecastList) {
            // すでに保存済みならスキップ（オプション）
            if (weatherRepository.findByDate(dto.getDate()).isPresent()) {
                continue;
            }

            Weather weather = new Weather();
            weather.setDate(dto.getDate());
            weather.setTempMax(dto.getTemp_max());
            weather.setTempMin(dto.getTemp_min());
            weather.setWeatherMain(dto.getWeather_main());
            weather.setHumidity(dto.getHumidity());
            weather.setWindSpeed(dto.getWind_speed());

            weatherRepository.save(weather);
        }
    }
}
