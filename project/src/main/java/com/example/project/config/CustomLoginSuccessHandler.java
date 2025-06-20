package com.example.project.config;

import com.example.project.service.WeatherApiService;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final WeatherApiService weatherApiService;

    public CustomLoginSuccessHandler(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        // 🌤 天気APIを呼び出してDB保存
        weatherApiService.fetchAndSaveWeatherForecast();

        // 任意の画面へリダイレクト（ホームページ）
        response.sendRedirect("/");
    }
}
