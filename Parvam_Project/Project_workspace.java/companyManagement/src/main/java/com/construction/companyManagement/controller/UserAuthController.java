package com.construction.companyManagement.controller;

import com.construction.companyManagement.dto.UserDTO;
import com.construction.companyManagement.model.User;
import com.construction.companyManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user-register"; 
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); 
        user.setRole(userDTO.getRole());

        userService.saveUser(user);

        return "redirect:/user/login"; 
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user-login"; 
    }
}