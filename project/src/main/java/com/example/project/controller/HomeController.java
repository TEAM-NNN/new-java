package com.example.project.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        DayOfWeek today = LocalDate.now().getDayOfWeek(); // 今日の日付の曜日を取得
        model.addAttribute("dayOfWeek", today); // モデルに曜日を追加
        return "home"; // home.html テンプレートを返す
    }
}
