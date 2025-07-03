package com.construction.companyManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.construction.companyManagement.model.ContactMessage;
import com.construction.companyManagement.service.ContactMessageService;

@Controller
@RequestMapping("/admin/messages")
public class AdminMessageController {

    @Autowired
    private ContactMessageService messageService;

    // Display all messages
    @GetMapping
    public String showMessages(Model model) {
        List<ContactMessage> messages = messageService.getAllMessages();
        model.addAttribute("messages", messages);
        return "admin/message"; // Thymeleaf view path
    }

    // Delete a message
    @PostMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Long id) {
        messageService.deleteById(id);
        return "redirect:/admin/messages?deleted";
    }
}