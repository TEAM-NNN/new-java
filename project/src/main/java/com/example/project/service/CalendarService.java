package com.example.project.service;

import com.example.project.model.DailyStatusDTO;
import com.example.project.entity.Beer;
import com.example.project.model.BeerSales;
import com.example.project.model.Weather;
import com.example.project.repository.BeerRepository;
import com.example.project.repository.BeerSalesRepository;
import com.example.project.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
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
     * 🔹 月ごとの日別状態取得
     */
    public List<DailyStatusDTO> getMonthlyStatus(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        return getStatusBetween(start, end);
    }

    /**
     * 🔹 期間指定で日ごとの販売＆天気を取得
     */
    public List<DailyStatusDTO> getStatusBetween(LocalDate start, LocalDate end) {

        // 🟡 ビールの情報を取得（id -> Beer）
        Map<Long, Beer> beerMap = beerRepository.findAll().stream()
                .filter(b -> b.getId() != null)
                .collect(Collectors.toMap(
                        Beer::getId,
                        Function.identity(),
                        (existing, replacement) -> existing // IDが重複する場合は、最初のものを残す
                ));

        // 🟡 売上記録を取得して日別の合計を計算
        List<BeerSales> salesList = beerSalesRepository.findByDateBetween(start, end);
        Map<LocalDate, Integer> totalSalesMap = new HashMap<>();
        for (BeerSales sale : salesList) {
            LocalDate date = sale.getDate();
            int quantity = sale.getQuantity();
            Beer beer = beerMap.get(sale.getBeerId());

            if (beer != null) {
                int price = beer.getPrice() != null ? beer.getPrice() : 0;
                int total = quantity * price;
                totalSalesMap.merge(date, total, Integer::sum);
            }
        }

        // 🟡 天気情報を取得して日付ごとの Map に格納する
        List<Weather> weatherList = weatherRepository.findByDateBetween(start, end);
        Map<LocalDate, String> weatherMap = weatherList.stream()
                .filter(w -> w.getDate() != null && w.getWeatherMain() != null)
                .collect(Collectors.toMap(Weather::getDate, Weather::getWeatherMain));

        // 🟡 カレンダー表示用のDTO
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
     * 🔸 天気の種類を絵文字アイコンに変換する
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