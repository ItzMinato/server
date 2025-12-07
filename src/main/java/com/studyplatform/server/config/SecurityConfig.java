package com.studyplatform.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // взагалі вимикаємо CSRF, щоб не було 403 на POST
                .csrf(csrf -> csrf.disable())

                // ДАЄМО ДОСТУП ДО ВСІХ ЕНДПОІНТІВ
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // вимикаємо стандартну форму логіну
                .formLogin(form -> form.disable())

                // вимикаємо Basic Auth
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
