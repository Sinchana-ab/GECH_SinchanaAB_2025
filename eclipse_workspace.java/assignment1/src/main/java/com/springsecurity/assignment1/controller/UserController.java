package com.springsecurity.assignment1.controller;

import com.springsecurity.assignment1.dto.UserDTO;
import com.springsecurity.assignment1.model.User;
import com.springsecurity.assignment1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    // Handle form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/login?success";
    }

    // Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
