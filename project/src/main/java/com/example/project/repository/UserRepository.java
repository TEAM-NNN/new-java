package com.example.project.repository;

import com.example.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // メールアドレスでユーザーを検索
    Optional<User> findByEmail(String email);
    
    // ユーザーが存在するか確認
    boolean existsByEmail(String email);
}
