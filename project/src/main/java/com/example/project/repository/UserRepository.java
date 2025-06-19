package com.example.project.repository;

import com.example.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // メールアドレスでユーザーを検索(論理削除されていないユーザー)
    Optional<User> findByEmailAndDeleteFalse(String email);

    //ユーザーが存在するか確認(論理削除されていないユーザー)
    boolean existsByEmailAndDeleteFalse(String email);
    
    // 論理削除されていない全ユーザーを取得(管理画面などで使用)
    List<User> findAllByDeleteFalse();
}
