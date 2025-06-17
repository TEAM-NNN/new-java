package com.example.project.service;

import com.example.project.model.*;
import com.example.project.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class CalendarService {

    private final BeerSalesRepository beerSalesRepository;
    private final WeatherRepository weatherRepository;

    public CalendarService(
            BeerSalesRepository beerSalesRepository,
            WeatherRepository weatherRepository
    ) {
        this.beerSalesRepository = beerSalesRepository;
        this.weatherRepository = weatherRepository;
    }

    /**
     * 指定年月のカレンダーデータ（日付ごとの売上・天気）を取得
     */
    public List<DailyStatusDTO> getMonthlyStatus(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        // 売上取得：指定月の日付範囲
        List<BeerSales> salesList = beerSalesRepository.findByDateBetween(start, end);
        Map<LocalDate, Integer> salesMap = new HashMap<>();

        for (BeerSales sale : salesList) {
            LocalDate date = sale.getDate();
            int amount = sale.getQuantity() * sale.getEnter();  // enter = 単価スナップショット
            salesMap.put(date, salesMap.getOrDefault(date, 0) + amount);
        }

        // DTOリスト生成：毎日の日付 + 売上 + 天気
        List<DailyStatusDTO> result = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            int totalSales = salesMap.getOrDefault(date, 0);
            String weatherMain = weatherRepository.findByDate(date)
                                    .map(Weather::getWeatherMain)
                                    .orElse("不明");
            result.add(new DailyStatusDTO(date, totalSales, weatherMain));
        }

        return result;
    }
}
