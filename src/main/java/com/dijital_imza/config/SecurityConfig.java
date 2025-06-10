package com.dijital_imza.config;

import com.dijital_imza.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                // CSRF korumasını kapat
                .csrf(csrf -> csrf.disable())
                // İstek izinleri
                .authorizeHttpRequests(auth -> auth
                        // Auth işlemleri için açık bırak
                        .requestMatchers("/api/auth/login", "/api/auth/signup").permitAll()
                        // Static dosyalar ve WebSocket handshake izinli
                        .requestMatchers("/uploads/**", "/ws/**").permitAll()
                        // Diğer tüm isteklerde JWT filtresi
                        .anyRequest().authenticated()
                )
                // JWT filtresini güvenlik zincirine ekle
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
