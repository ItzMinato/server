package com.studyplatform.server.service;

import com.studyplatform.server.dto.UserLoginRequest;
import com.studyplatform.server.dto.UserRegisterRequest;
import com.studyplatform.server.dto.UserResponse;
import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================================
    //           РЕЄСТРАЦІЯ
    // ================================
    public UserResponse register(UserRegisterRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Користувач з таким username вже існує!");
        }

        // Конвертація ролі (String → ENUM)
        User.Role role;
        try {
            role = User.Role.valueOf(req.getRole().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Невідома роль! Використовуйте STUDENT або TEACHER.");
        }

        // Хешування пароля
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        User user = new User(
                req.getUsername(),
                encodedPassword,
                role
        );

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    // ================================
    //              ЛОГІН
    // ================================
    public UserResponse login(UserLoginRequest req) {

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Невірний username або пароль!"));

        // Перевірка пароля через BCrypt
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Невірний username або пароль!");
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    // ================================
    //      ОТРИМАННЯ КОРИСТУВАЧА
    // ================================
    public UserResponse getUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Користувача не знайдено!"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }
}
