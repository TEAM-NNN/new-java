package com.example.project.controller;

import com.example.project.repository.AccountRepository;
import com.example.project.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public String getAccounts(Model model) {
        List<Account> userList = accountRepository.findAll();
        model.addAttribute("userList", userList);
        return "account";
    }
}
