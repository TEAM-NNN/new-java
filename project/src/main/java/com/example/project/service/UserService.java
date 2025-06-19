package com.example.project.service;

import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndDeleteFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません、もしくは削除されました: " + email));


        // ユーザーの権限を作成
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // ユーザーが管理者かどうかをチェック;
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  // 管理者の場合はROLE_ADMINを追加
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));   // 一般ユーザーの場合はROLE_USERを追加
        }
        
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
    

