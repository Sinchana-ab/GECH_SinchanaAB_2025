package com.construction.companyManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.construction.companyManagement.model.ContactMessage;
import com.construction.companyManagement.service.ContactMessageService;

@Controller
public class ContactController {
    private final ContactMessageService service;

    public ContactController(ContactMessageService service) {
        this.service = service;
    }

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactMessage contactMessage) {
        service.save(contactMessage);
        return "redirect:/contact?success";
    }
}