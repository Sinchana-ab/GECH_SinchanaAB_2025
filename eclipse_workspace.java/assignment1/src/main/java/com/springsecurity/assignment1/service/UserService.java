package com.springsecurity.assignment1.service;

import com.springsecurity.assignment1.dto.UserDTO;
import com.springsecurity.assignment1.model.User;
import com.springsecurity.assignment1.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    

    // Register user using UserDTO
    public void registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername()) ; 
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }


    // Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
