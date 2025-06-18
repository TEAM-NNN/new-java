package com.example.project.controller;

import com.example.project.dto.BeerItem;
import com.example.project.dto.BeerForm;
import com.example.project.entity.BeerSale;
import com.example.project.repository.BeerSaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class InputController {

    @Autowired
    private BeerSaleRepository beerSaleRepository;

    @GetMapping("/input")
    public String showInputForm(Model model) {

          LocalDate today = LocalDate.now();

             boolean alreadySaved = beerSaleRepository.existsByDate(today);
   /*  model.addAttribute("alreadySaved", alreadySaved); 

    if (beerSaleRepository.existsByDate(today)) {
        model.addAttribute("message", "本日の販売実績はすでに入力済みです。");
        model.addAttribute("today", today);
        return "input-form"; 
    } */
        List<BeerItem> beerList = Arrays.asList(
                new BeerItem(1, "ホワイトビール", 900, "4901234567894"),
                new BeerItem(2, "ラガー", 800, "4512345678907"),
                new BeerItem(3, "ペールエール", 1000, "4987654321097"),
                new BeerItem(4, "フルーツビール", 1000, "4545678901234"),
                new BeerItem(5, "黒ビール", 1200, "4999999999996"),
                new BeerItem(6, "IPA", 900, "4571234567892")
        );

        BeerForm beerForm = new BeerForm();
        beerForm.setBeerList(beerList);

    model.addAttribute("form", beerForm);
    model.addAttribute("today", today);
    model.addAttribute("alreadySaved", alreadySaved);

    // ★入力済み時だけメッセージを追加（returnしない！）
    if (alreadySaved) {
        model.addAttribute("message", "本日の販売実績はすでに入力済みです。");
    }

    return "input-form";
   
    }

    @PostMapping("/input/save")
    public String saveResult(@ModelAttribute("form") BeerForm form, Model model) {
        LocalDate today = LocalDate.now();

        if (beerSaleRepository.existsByDate(today)) {
        model.addAttribute("message", "本日の実績は既に保存されています。");
        model.addAttribute("form", form);
        model.addAttribute("today", today);
        model.addAttribute("alreadySaved", true); // ←これも忘れずに
        return "input-form"; // 保存済みでも同じ画面に戻す
    }

        for (BeerItem item : form.getBeerList()) {
System.out.println("Saving beer_id = " + item.getNo()); // ← 追加！

            BeerSale sale = new BeerSale();
            sale.setDate(today);
            sale.setBeerId(item.getNo());              // BeerItemのnoをIDとして使う前提
            sale.setQuantity(item.getSoldCount());
            sale.setEnterUserId(1);                    // 仮にユーザーIDを固定

            beerSaleRepository.save(sale);
        }

        model.addAttribute("form", form);
        model.addAttribute("message", "保存しました！");
        model.addAttribute("today", today);
        model.addAttribute("alreadySaved", true); // 保存後もボタンを消す

        return "input-form";
    }
}

