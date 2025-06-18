package com.example.project.controller;

import com.example.project.model.DailyStatusDTO;
import com.example.project.service.CalendarService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // FullCalendar 页面
    @GetMapping("/calendar")
    public String showCalendarPage() {
        return "calendar";
    }

    // FullCalendar JSON 数据
    @GetMapping("/api/calendar-events")
    @ResponseBody
    public List<Map<String, Object>> getCalendarEvents() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        List<DailyStatusDTO> list = calendarService.getMonthlyStatus(year, month);

        List<Map<String, Object>> events = new ArrayList<>();
        for (DailyStatusDTO dto : list) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", dto.getTotalSales() + "円 (" + dto.getWeatherMain() + ")");
            event.put("start", dto.getDate().toString());
            event.put("allDay", true);
            events.add(event);
        }

        return events;
    }
}