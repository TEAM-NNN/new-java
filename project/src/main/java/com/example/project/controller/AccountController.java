package com.example.project.controller;

import com.example.project.repository.AccountRepository;
import com.example.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public String getAccounts(Model model) {
        List<User> userList = accountRepository.findAll();
        model.addAttribute("userList", userList);
        return "account";
    }

    @GetMapping("/accounts/new")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new User());
        return "create";
    }

    @PostMapping("/accounts/create")
    public String createAccount(@ModelAttribute User account) {
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);

        accountRepository.save(account);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/edit/{id}")
    public String editAccount(@PathVariable Long id, Model model) {
        User account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID: " + id));
        model.addAttribute("account", account);
        return "account-edit";
    }

    @PostMapping("/accounts/update")
    public String updateAccount(@ModelAttribute User account) {
    User existing = accountRepository.findById(account.getId())
        .orElseThrow(() -> new IllegalArgumentException("更新対象のアカウントが見つかりません: " + account.getId()));

    existing.setName(account.getName());
    existing.setEmail(account.getEmail());
    existing.setAdmin(account.isAdmin());

    // パスワードが空でないときだけ変更
    if (account.getPassword() != null && !account.getPassword().isEmpty()) {
        // 入力されたパスワードが既存のものと違うときだけ更新
        if (!passwordEncoder.matches(account.getPassword(), existing.getPassword())) {
            existing.setPassword(passwordEncoder.encode(account.getPassword()));
        }
    }

    accountRepository.save(existing);
    return "redirect:/accounts";
}

}
