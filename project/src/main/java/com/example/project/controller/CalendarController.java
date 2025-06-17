package com.example.project.controller;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/calendar")
    public String showCalendar(){

        if(month == null) month = YearMonth.now();

    }
}
