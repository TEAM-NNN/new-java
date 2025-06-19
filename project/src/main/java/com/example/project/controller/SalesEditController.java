package com.example.project.controller;

import com.example.project.dto.BeerForm;
import com.example.project.dto.BeerItem;
import com.example.project.entity.BeerSale;
import com.example.project.repository.BeerSaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class SalesEditController {

    @Autowired
    private BeerSaleRepository beerSaleRepository;

    // --- 実績入力画面の表示 ---
    @GetMapping("/edit")
    public String showInputForm(Model model) {
        LocalDate today = LocalDate.now();
List<BeerSale> salesList = beerSaleRepository.findByDate(today);
        List<BeerItem> beerList = Arrays.asList(
                new BeerItem(1, "ホワイトビール", 900, "4901234567894"),
                new BeerItem(2, "ラガー", 800, "4512345678907"),
                new BeerItem(3, "ペールエール", 1000, "4987654321097"),
                new BeerItem(4, "フルーツビール", 1000, "4545678901234"),
                new BeerItem(5, "黒ビール", 1200, "4999999999996"),
                new BeerItem(6, "IPA", 900, "4571234567892")
        );

       model.addAttribute("salesList", salesList);
    model.addAttribute("today", today);

        return "edit-form";
    }

  
    

    // --- 修正処理：1件だけ更新 ---
    @PostMapping("/edit/update")
public String updateOneRecord(
    @RequestParam("id") Integer id,
    @RequestParam("quantity") Integer quantity,
    Model model) {

    Optional<BeerSale> optional = beerSaleRepository.findById(id);

    if (optional.isPresent()) {
        BeerSale sale = optional.get();
        sale.setQuantity(quantity);
        beerSaleRepository.save(sale);
        model.addAttribute("message", "修正しました！");
    } else {
        model.addAttribute("message", "修正できませんでした。");
    }

    // 再描画用のデータ取得
    List<BeerSale> salesList = beerSaleRepository.findByDate(LocalDate.now());
    model.addAttribute("salesList", salesList);
    model.addAttribute("today", LocalDate.now());

    return "edit-form";
}
}



