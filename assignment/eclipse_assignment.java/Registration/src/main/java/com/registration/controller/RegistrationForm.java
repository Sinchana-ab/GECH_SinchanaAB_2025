package com.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.registration.dto.RegistrationDTO;
import com.registration.model.Registration;
import com.registration.service.RegistrationService;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationForm {

    @Autowired
    private RegistrationService service;

    
    @GetMapping("/register")
    public String showRegistration(Model model) {
        model.addAttribute("registration", new RegistrationDTO());
        return "register";
    }
    @PostMapping("/register")
    public String registerRegistration(
        @Valid @ModelAttribute("registration") RegistrationDTO registrationDTO,
        BindingResult result,
        Model model) {

        if (result.hasErrors()) {
            System.out.println("Validation failed: " + result.getAllErrors());
            return "register"; 
        }

        service.register(registrationDTO);
        System.out.println("Registration successful, redirecting...");
        return "redirect:/registration/list"; 
    }


    @GetMapping("/list")
    public String listUsers(Model model) {
        List<Registration> users = service.getAllRegistration();
        model.addAttribute("register", users);
        return "register-list";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Registration user = service.getRegistrationById(id);
        if (user == null) {
            return "redirect:/registration/list"; // Redirect if user not found
        }
        model.addAttribute("registration", user);
        return "register-edit";
    }

    // Handle update operation with validation
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid @ModelAttribute RegistrationDTO registrationDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registration", registrationDTO);
            return "register-edit"; // Return edit form with errors
        }
        service.updateRegistration(id, registrationDTO);
        return "redirect:/registration/list";
    }

    // Delete a registration entry
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        service.deleteRegistration(id);
        return "redirect:/registration/list";
    }
}
