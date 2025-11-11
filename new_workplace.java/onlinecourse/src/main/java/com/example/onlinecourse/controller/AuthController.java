package com.example.onlinecourse.controller;

import com.example.onlinecourse.dto.AuthRequests;
import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.UserRepository;
import com.example.onlinecourse.service.AuthService;
import com.example.onlinecourse.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;
    @Autowired private UserRepository userRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;

    // Register student
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequests.RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok(Map.of("message", "Registered successfully as Student"));
    }

    // Admin adds instructor
    @PostMapping("/add-instructor")
    public ResponseEntity<?> addInstructor(@RequestBody AuthRequests.RegisterRequest req) {
        authService.addInstructor(req);
        return ResponseEntity.ok(Map.of("message", "Instructor added successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequests.LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email, req.password)
            );

            User user = userRepository.findByEmail(req.email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // ✅ Generate token including role and email
            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", user.getEmail(),
                    "role", user.getRole().name()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

}
