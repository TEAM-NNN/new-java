package com.example.project.controller;

import com.example.project.dto.ForecastItem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

@Controller
public class ForecastController {



    @Value("${forecast.api.url}")
    private String apiUrl;

    @GetMapping("/forecast")
    public String showForecast(Model model) {
        // 1. 次の発注日を計算（今日が月曜・木曜か？）
        LocalDate nextOrderDate = getNextOrderDate();

        // 2. Python APIを呼び出して、予測データを取得
        String url = apiUrl;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ForecastItem[]> response = restTemplate.getForEntity(url, ForecastItem[].class);
        List<ForecastItem> forecastList = Arrays.asList(response.getBody());

        // 4. HTML側にデータを渡す
        model.addAttribute("forecastList", forecastList);
        model.addAttribute("orderDate", nextOrderDate);
        return "forecast"; // forecast.html を表示
    }

    // 発注日（月曜 or 木曜）を自動で計算する
    private LocalDate getNextOrderDate() {
        LocalDate today = LocalDate.now();
        DayOfWeek dow = today.getDayOfWeek();
        if (dow == DayOfWeek.MONDAY || dow == DayOfWeek.THURSDAY) return today;
        if (dow.getValue() < 4) return today.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        return today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }
}

