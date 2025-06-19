package com.example.project.controller;

import com.example.project.model.DailyStatusDTO;
import com.example.project.service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;
import java.util.*;

@Controller
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/calendar")
    public String showCalendarPage(@RequestParam(value = "year", required = false) Integer year,
                                    @RequestParam(value = "month", required = false) Integer month,
                                    Model model) {

        // 今日を基準に表示年月を決定
        LocalDate today = LocalDate.now();
        int displayYear = (year != null) ? year : today.getYear();
        int displayMonth = (month != null) ? month : today.getMonthValue();

        YearMonth ym = YearMonth.of(displayYear, displayMonth);
        LocalDate firstDay = ym.atDay(1);
        LocalDate lastDay = ym.atEndOfMonth();

        List<DailyStatusDTO> dailyList = calendarService.getStatusBetween(firstDay, lastDay);

        // DTO をマップ化（日付 → DTO）
        Map<LocalDate, DailyStatusDTO> dtoMap = new HashMap<>();
        for (DailyStatusDTO dto : dailyList) {
            dtoMap.put(dto.getDate(), dto);
        }

        // カレンダーに表示する日付情報を構築
        List<Map<String, Object>> days = new ArrayList<>();
        for (LocalDate d = firstDay; !d.isAfter(lastDay); d = d.plusDays(1)) {
            DailyStatusDTO dto = dtoMap.getOrDefault(d, new DailyStatusDTO(d, 0, "❓"));
            Map<String, Object> dayMap = new HashMap<>();
            dayMap.put("date", d);
            dayMap.put("totalSales", dto.getTotalSales());
            dayMap.put("weather", dto.getWeatherMain());
            days.add(dayMap);
        }

        // 前後の月の情報（ボタン用）
        YearMonth prevMonth = ym.minusMonths(1);
        YearMonth nextMonth = ym.plusMonths(1);

        model.addAttribute("year", displayYear);
        model.addAttribute("month", displayMonth);
        model.addAttribute("startDayOfWeek", firstDay.getDayOfWeek().getValue() % 7); // 日曜始まり
        model.addAttribute("days", days);
        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", prevMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());
        model.addAttribute("today", today);

        return "calendar";
    }
}
