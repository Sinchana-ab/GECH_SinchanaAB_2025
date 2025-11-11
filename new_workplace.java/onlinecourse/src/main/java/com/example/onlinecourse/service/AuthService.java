package com.example.onlinecourse.service;

import com.example.onlinecourse.dto.AuthRequests;
import com.example.onlinecourse.model.Role;
import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public void register(AuthRequests.RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));
        user.setRole(Role.ROLE_STUDENT);
        userRepository.save(user);
    }

    public void addInstructor(AuthRequests.RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));
        user.setRole(Role.ROLE_INSTRUCTOR);
        userRepository.save(user);
    }
}
