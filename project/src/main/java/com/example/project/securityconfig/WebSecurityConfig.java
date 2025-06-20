// package com.example.project.securityconfig;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;


// @Configuration
// public class WebSecurityConfig {

//     @Bean
//     public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//         UserDetails user = User.builder()
//             .username("admin")
//             .password(passwordEncoder.encode("password")) // ← 必ずエンコードすること
//             .roles("ADMIN")
//             .build();

//         return new InMemoryUserDetailsManager(user);
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
