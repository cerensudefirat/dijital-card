package com.dijital_imza.config;

import com.dijital_imza.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Auth işlemleri herkese açık
                        .requestMatchers("/api/auth/login", "/api/auth/signup").permitAll()
                        // Tüm kullanıcıları listeleyen GET endpoint’i herkese açık
                        .requestMatchers(HttpMethod.GET, "/api/auth/list").permitAll()
                        // Statik dosyalar herkese açık
                        .requestMatchers("/uploads/**").permitAll()
                        // WebSocket handshake ve SockJS uç noktaları authenticated
                        .requestMatchers("/ws/**").authenticated()
                        // Geriye kalan tüm uç noktalar JWT ile korunacak
                        .anyRequest().authenticated()
                )
                // JWT filtresini UsernamePasswordAuthenticationFilter’dan önce ekle
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
