package com.construction.companyManagement.controller;

import com.construction.companyManagement.dto.UserDTO;
import com.construction.companyManagement.model.User;
import com.construction.companyManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserAuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        System.out.println("--- REGISTRATION ATTEMPT ---");
        System.out.println("Received UserDTO: " + userDTO.toString());
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER"); 

        System.out.println("User object to save: " + user.toString());

        try {
            userService.saveUser(user);
            System.out.println("User '" + user.getUsername() + "' saved successfully to the database.");
        } catch (Exception e) {
            System.err.println("!!! FAILED TO SAVE USER !!!");
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "register";
        }

        System.out.println("Registration successful. Redirecting to login page.");
        return "redirect:/login";
    }
    
    @GetMapping("/user/dashboard")
    public String userDashboard() {
        // This method simply returns the view name for the user dashboard.
        // Spring Security handles authentication and ensures only authorized users can access this.
        return "user/dashboard";
    }
    
    @GetMapping("/login")
    public String showCommonLoginForm() {
        return "login";
    }
}
