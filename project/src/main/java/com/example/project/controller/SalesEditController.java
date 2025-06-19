package com.example.project.controller;

import com.example.project.dto.BeerFormEdit;
import com.example.project.dto.BeerItemEdit;
import com.example.project.entity.BeerSaleEdit;
import com.example.project.repository.BeerSaleRepositoryEdit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class SalesEditController {

    @Autowired
    private BeerSaleRepositoryEdit beerSaleRepository;

    // --- 表示 ---
    @GetMapping("/edit")
    public String showInputForm(Model model) {
        LocalDate today = LocalDate.now();
        List<BeerSaleEdit> salesList = beerSaleRepository.findByDate(today);

        // ビールマスタの一覧
        List<BeerItemEdit> beerList = Arrays.asList(
            new BeerItemEdit(1, "ホワイトビール", 900, "4901234567894"),
            new BeerItemEdit(2, "ラガー", 800, "4512345678907"),
            new BeerItemEdit(3, "ペールエール", 1000, "4987654321097"),
            new BeerItemEdit(4, "フルーツビール", 1000, "4545678901234"),
            new BeerItemEdit(5, "黒ビール", 1200, "4999999999996"),
            new BeerItemEdit(6, "IPA", 900, "4571234567892")
        );

        // 実績データに BeerItem を対応付け
        for (BeerSaleEdit sale : salesList) {
            for (BeerItemEdit item : beerList) {
                if (item.getNo() == sale.getBeerId()) {
                    item.setSoldCount(sale.getQuantity());
                    sale.setBeer(item);
                    break;
                }
            }
        }

        // 重複削除
        Map<Integer, BeerSaleEdit> uniqueMap = new LinkedHashMap<>();
        for (BeerSaleEdit sale : salesList) {
            uniqueMap.putIfAbsent(sale.getBeerId(), sale);
        }
        List<BeerSaleEdit> uniqueSalesList = new ArrayList<>(uniqueMap.values());

        uniqueSalesList.sort(Comparator.comparing(BeerSaleEdit::getBeerId));

        BeerFormEdit form = new BeerFormEdit();
        form.setBeerList(beerList);

        model.addAttribute("form", form);
        model.addAttribute("salesList", uniqueSalesList);
        model.addAttribute("today", today);
        return "edit-form";
    }

    // --- 更新 ---
    @PostMapping("/edit/update")
    public String updateOneRecord(
       
            @RequestParam("beerId") Integer beerId,
            @RequestParam("quantity") Integer quantity,
            Model model) {

                LocalDate today = LocalDate.now();


System.out.println("更新対象ID: " + beerId);
  System.out.println("更新数量: " + quantity);

        Optional<BeerSaleEdit> optional = beerSaleRepository.findByBeerIdAndDate(beerId, today);
        if (optional.isPresent()) {
            BeerSaleEdit sale = optional.get();
            sale.setQuantity(quantity);
            beerSaleRepository.save(sale);
            model.addAttribute("message", "修正しました！");
        } else {
            model.addAttribute("message", "修正できませんでした。");
        }

        return showInputForm(model); // 再表示
    }
} 



