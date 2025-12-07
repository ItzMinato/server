package com.studyplatform.server.controller;

import com.studyplatform.server.entity.User;
import com.studyplatform.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ================================
    //  РЕЄСТРАЦІЯ
    // ================================
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        try {
            userService.registerUser(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole()
            );
            return "User registered successfully!";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

    // ================================
    //  ЛОГІН
    // ================================
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        try {
            userService.loginUser(user.getUsername(), user.getPassword());
            return "Login successful!";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }

    // ================================
    //  ОТРИМАННЯ КОРИСТУВАЧА
    // ================================
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }
}
