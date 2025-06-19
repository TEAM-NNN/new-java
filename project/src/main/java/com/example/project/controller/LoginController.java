package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // ログインページの表示
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // resources/templates/login.html を返す
    }
}


    
