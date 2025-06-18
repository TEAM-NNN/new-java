package com.example.project.service;

import com.example.project.model.*;
import com.example.project.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private final BeerSalesRepository beerSalesRepository;
    private final WeatherRepository weatherRepository;
    private final BeerRepository beerRepository;

    public CalendarService(BeerSalesRepository beerSalesRepository,
                           WeatherRepository weatherRepository,
                           BeerRepository beerRepository) {
        this.beerSalesRepository = beerSalesRepository;
        this.weatherRepository = weatherRepository;
        this.beerRepository = beerRepository;
    }

    /**
     * 🔹 月単位の売上・天気取得（旧方式）
     */
    public List<DailyStatusDTO> getMonthlyStatus(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return getStatusBetween(start, end);  // 共通ロジックに委譲
    }

    /**
     * 🔹 任意の期間の売上・天気情報を取得（カレンダー表示用）
     */
    public List<DailyStatusDTO> getStatusBetween(LocalDate start, LocalDate end) {

        // 🟡 ビール情報取得（id → Beer）
        Map<Long, Beer> beerMap = beerRepository.findAll().stream()
                .collect(Collectors.toMap(Beer::getId, beer -> beer));

        // 🟡 売上取得（合計金額を日付ごとに集計）
        List<BeerSales> salesList = beerSalesRepository.findByDateBetween(start, end);
        Map<LocalDate, Integer> totalSalesMap = new HashMap<>();
        for (BeerSales sale : salesList) {
            LocalDate date = sale.getDate();
            int quantity = sale.getQuantity();
            Beer beer = beerMap.get(sale.getBeerId());

            if (beer != null) {
                int price = beer.getPrice();
                int total = quantity * price;
                totalSalesMap.merge(date, total, Integer::sum);
            }
        }

        // 🟡 天気取得（null安全に）
        List<Weather> weatherList = weatherRepository.findByDateBetween(start, end);
        Map<LocalDate, String> weatherMap = weatherList.stream()
                .filter(w -> w.getDate() != null && w.getWeatherMain() != null)
                .collect(Collectors.toMap(Weather::getDate, Weather::getWeatherMain));

        // 🟡 日別 DTO 作成
        List<DailyStatusDTO> result = new ArrayList<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            int totalSales = totalSalesMap.getOrDefault(d, 0);
            String weatherMain = weatherMap.getOrDefault(d, "不明");
            String weatherIcon = getWeatherIcon(weatherMain);
            result.add(new DailyStatusDTO(d, totalSales, weatherIcon));
        }

        return result;
    }

    /**
     * 🔸 天気種類をアイコンに変換（不明も対応）
     */
    private String getWeatherIcon(String weatherMain) {
        return switch (weatherMain) {
            case "晴" -> "☀️";
            case "曇" -> "☁️";
            case "雨" -> "🌧️";
            case "雪" -> "❄️";
            case "雷" -> "⛈️";
            case "大雨" -> "🌧️🌧️";
            default -> "❓";
        };
    }
}
