package com.bookmyshow.controller;

import com.bookmyshow.dto.JwtResponse;
import com.bookmyshow.dto.LoginRequest;
import com.bookmyshow.model.User;
import com.bookmyshow.security.jwt.JwtService;
import com.bookmyshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());

        // ✅ Generate token using full user object so role is embedded in JWT
        String token = jwtService.generateToken(user);

        // ⬇️ Return token, role, and name to frontend
        return new JwtResponse(token, user.getName(), user.getRole());
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'THEATRE')")
    public User getCurrentUser(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
