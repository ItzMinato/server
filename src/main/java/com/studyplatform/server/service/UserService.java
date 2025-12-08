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
    private final ActivityLogService activityLogService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ActivityLogService activityLogService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activityLogService = activityLogService;
    }

    public UserResponse register(UserRegisterRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Користувач з таким username вже існує!");
        }

        User.Role role = User.Role.valueOf(req.getRole().toUpperCase());

        User user = new User(
                req.getUsername(),
                passwordEncoder.encode(req.getPassword()),
                role
        );

        userRepository.save(user);

        // LOG
        activityLogService.log(user, "User registered");

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    public UserResponse login(UserLoginRequest req) {

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Невірний username або пароль!"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Невірний username або пароль!");
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    public User getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

}
