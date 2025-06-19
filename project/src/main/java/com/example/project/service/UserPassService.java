package com.example.project.service;

import com.example.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPassService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserPassService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean changePassword(String email, String currentPassword, String newPassword) {
       return userRepository.findByEmailAndDeleteFalse(email).map(user -> {
            // 現在のパスワードが正しいか確認
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                return false; // 現在のパスワードが間違っている場合はfalseを返す
            }
            // 🔒 新しいパスワードが現在と同じかチェック
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new IllegalArgumentException("新しいパスワードが現在のパスワードと同じです。");
            }
                // 新しいパスワードをエンコードして保存
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true; // パスワード変更成功
        }).orElse(false); // ユーザーが存在しない場合はfalseを返す
    }
}          