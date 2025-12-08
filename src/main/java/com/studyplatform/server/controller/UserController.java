package com.studyplatform.server.controller;

import com.studyplatform.server.dto.UserLoginRequest;
import com.studyplatform.server.dto.UserRegisterRequest;
import com.studyplatform.server.dto.UserResponse;
import com.studyplatform.server.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================================
    //             РЕЄСТРАЦІЯ
    // ================================
    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest req) {
        return userService.register(req);
    }

    // ================================
    //               ЛОГІН
    // ================================
    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest req) {
        return userService.login(req);
    }

    // ================================
    //      ОТРИМАТИ КОРИСТУВАЧА
    // ================================
    @GetMapping("/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userService.getUser(username);
    }
}
