package com.example.project.controller;

import com.example.project.dto.BeerFormEdit;
import com.example.project.dto.BeerItemEdit;
import com.example.project.entity.Beer;
import com.example.project.entity.BeerSaleEdit;
import com.example.project.repository.BeerSaleRepositoryEdit;
import com.example.project.repository.BeerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class SalesEditController {

    @Autowired
    private BeerSaleRepositoryEdit beerSaleRepository;
    @Autowired BeerRepository beerRepository;

    // --- 表示 ---
    @GetMapping("/edit")
    public String showInputForm(Model model) {
        LocalDate today = LocalDate.now();
        List<BeerSaleEdit> salesList = beerSaleRepository.findByDate(today);

        List<Beer> beerEntityList = beerRepository.findAll();
        List<BeerItemEdit> beerList = beerEntityList.stream()
        .map(beer -> new BeerItemEdit(
            beer.getId().intValue(),     // BeerItemはint、BeerはLong
            beer.getName(),
            beer.getPrice(),
            beer.getJanCode()
        ))
        .collect(Collectors.toList());
    
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
        Map<Long, BeerSaleEdit> uniqueMap = new LinkedHashMap<>();

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



