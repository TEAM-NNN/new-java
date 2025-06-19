package com.example.project.controller;

import com.example.project.service.UserPassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChangePasswordController {

    private final UserPassService userPassService;

    public ChangePasswordController(UserPassService userPassService) {
        this.userPassService = userPassService;
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";  // resources/templates/change-password.html を返す
    }

    @PostMapping("/change-password")
    public String processPasswordChange(
            @RequestParam("email") String email,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model
    ) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "新しいパスワードと確認用パスワードが一致しません。");
            return "change-password";  // エラーメッセージを表示するために同じフォームを返す
        }

        try {
            boolean success = userPassService.changePassword(email, currentPassword, newPassword);
            if (success) {
                model.addAttribute("message", "パスワードが正常に変更されました。ログインしてください。");
            } else {
                model.addAttribute("error", "現在のパスワードが正しくありません、またはユーザーが存在しません。");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "change-password";  // 成功またはエラーのメッセージを表示するために同じフォームを返す
    }
}
