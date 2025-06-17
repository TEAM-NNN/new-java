package com.example.project.controller;

import com.example.project.model.BeerSalesDetailDTO;
import com.example.project.service.DetailService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DetailController {

    private final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    // 明细页面
    @GetMapping("/detail")
    public String showDetail(@RequestParam("date") String dateStr, Model model) {
        LocalDate date = LocalDate.parse(dateStr);
        List<BeerSalesDetailDTO> details = detailService.getDetailsByDate(date);
        model.addAttribute("date", date);
        model.addAttribute("details", details);
        return "detail";
    }
}
