package com.employee.Management.controller;
import com.employee.Management.dto.UserDTO;
import com.employee.Management.model.Role;
import com.employee.Management.repository.RoleRepository;
import com.employee.Management.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors; // Import Collectors

@Controller
public class AuthController {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        List<Role> roles = roleRepo.findAll();
        // Filter out the "ADMIN" role from the list presented to the user
        List<Role> nonAdminRoles = roles.stream()
                                        .filter(role -> !"ADMIN".equals(role.getName()))
                                        .collect(Collectors.toList());
        model.addAttribute("roles", nonAdminRoles); // Pass only non-admin roles
        model.addAttribute("registerUserDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute UserDTO registerUserDTO) {
        authService.registerUser(registerUserDTO);
        return "redirect:/login?registered=true";
    }
}
