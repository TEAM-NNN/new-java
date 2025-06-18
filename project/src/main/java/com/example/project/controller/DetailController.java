package com.example.project.controller;

import com.example.project.model.BeerSalesDetailDTO;
import com.example.project.service.DetailService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Controller
public class DetailController {

    private final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/detail")
    public String showDetail(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             Model model) {

        List<BeerSalesDetailDTO> details = detailService.getDetailsByDate(date);
        int totalAmount = details.stream()
                .mapToInt(BeerSalesDetailDTO::getTotalSales)
                .sum();

        model.addAttribute("date", date);
        model.addAttribute("details", details);
        model.addAttribute("totalAmount", totalAmount);  // ← 合計金額をビューに渡す

        return "detail";
    }
}
