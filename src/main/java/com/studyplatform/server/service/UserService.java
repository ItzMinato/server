package com.studyplatform.server.service;

import com.studyplatform.server.entity.User;
import com.studyplatform.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ================================
    //  РЕЄСТРАЦІЯ
    // ================================
    public void registerUser(String username, String password, String role) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Користувач з таким username вже існує!");
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    // ================================
    //  ЛОГІН
    // ================================
    public User loginUser(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Невірний username або пароль!")
                );

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Невірний username або пароль!");
        }

        return user;
    }

    // ================================
    //  ОТРИМАННЯ КОРИСТУВАЧА
    // ================================
    public User getUser(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Користувача не знайдено!")
                );
    }
}
