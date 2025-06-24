package com.example.project.controller;

import com.example.project.dto.BeerItem;
import com.example.project.dto.BeerForm;
import com.example.project.entity.BeerSaleEdit;
import com.example.project.repository.BeerSaleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.BeerRepository;
import com.example.project.entity.Beer;
import com.example.project.model.User;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class InputController {

    @Autowired
    private BeerSaleRepository beerSaleRepository;
    @Autowired
    private BeerRepository beerRepository;

    @GetMapping("/input")
    public String showInputForm(@AuthenticationPrincipal UserDetails user,Model model) {

          LocalDate today = LocalDate.now();

             boolean alreadySaved = beerSaleRepository.existsByDate(today);
  
      List<Beer> beerEntityList = beerRepository.findAll();

   // ② Beer → BeerItem に変換
    List<BeerItem> beerList = beerEntityList.stream()
        .map(beer -> new BeerItem(
            beer.getId().intValue(),     // BeerItemはint、BeerはLong
            beer.getName(),
            beer.getPrice(),
            beer.getJanCode()
        ))
        .collect(Collectors.toList());

        BeerForm beerForm = new BeerForm();
        beerForm.setBeerList(beerList);

    model.addAttribute("form", beerForm);
    model.addAttribute("today", today);
    model.addAttribute("alreadySaved", alreadySaved);
    model.addAttribute("username", user.getUsername()); // 初期化

    // ★入力済み時だけメッセージを追加（returnしない！）
    if (alreadySaved) {
        model.addAttribute("message", "本日の販売実績はすでに入力済みです。");
    }

    return "input-form";
   
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/input/save")
    public String saveResult(@ModelAttribute("form") BeerForm form,
                         @AuthenticationPrincipal UserDetails userDetails,
                         Model model){
        LocalDate today = LocalDate.now();

        if (beerSaleRepository.existsByDate(today)) {
        model.addAttribute("message", "本日の実績は既に保存されています。");
        model.addAttribute("form", form);
        model.addAttribute("today", today);
        model.addAttribute("alreadySaved", true); // ←これも忘れずに
        return "input-form"; // 保存済みでも同じ画面に戻す
    }

    // ログイン中のユーザーのメールアドレスからUserエンティティを取得
        String email = userDetails.getUsername();
        User loginUser = userRepository.findByEmailAndDeleteFalse(email)
        .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません: " + email));


        for (BeerItem item : form.getBeerList()) {
        System.out.println("Saving beer_id = " + item.getNo()); 

            BeerSaleEdit sale = new BeerSaleEdit();
            sale.setDate(today);
            sale.setBeerId((long) item.getNo());
            sale.setQuantity(item.getSoldCount());
            sale.setUserId(loginUser.getId());       

            beerSaleRepository.save(sale);
        }

        model.addAttribute("form", form);
        model.addAttribute("message", "保存しました！");
        model.addAttribute("today", today);
        model.addAttribute("alreadySaved", true); // 保存後もボタンを消す

        return "input-form";
    }
}

