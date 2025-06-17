package com.example.project.controller;

import com.example.project.model.*;
import com.example.project.service.CalendarService;
import com.example.project.service.DetailService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CalendarController {

    private final CalendarService calendarService;
    private final DetailService detailService;

    public CalendarController(CalendarService calendarService, DetailService detailService) {
        this.calendarService = calendarService;
        this.detailService = detailService;
    }

    /**
     * 📅 カレンダー画面
     * URL: /calendar?year=2025&month=6
     */
    @GetMapping("/calendar")
    public String showCalendar(@RequestParam(required = false) Integer year,
                               @RequestParam(required = false) Integer month,
                               Model model) {
        LocalDate today = LocalDate.now();
        int y = (year != null) ? year : today.getYear();
        int m = (month != null) ? month : today.getMonthValue();

        List<DailyStatusDTO> statusList = calendarService.getMonthlyStatus(y, m);

        model.addAttribute("statusList", statusList);
        model.addAttribute("year", y);
        model.addAttribute("month", m);
        return "calendar";
    }

    /**
     * 📄 明細画面
     * URL: /detail?date=2025-06-17
     */
    @GetMapping("/detail")
    public String showDetail(@RequestParam("date") String dateStr, Model model) {
        LocalDate date = LocalDate.parse(dateStr);
        List<BeerSalesDetailDTO> details = detailService.getDetailsByDate(date);

        model.addAttribute("date", date);
        model.addAttribute("details", details);
        return "detail";
    }
}
