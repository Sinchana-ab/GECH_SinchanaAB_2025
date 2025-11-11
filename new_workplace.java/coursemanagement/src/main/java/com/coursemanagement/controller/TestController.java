package com.coursemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coursemanagement.model.User;
import com.coursemanagement.service.EmailService;

//Test Controller for development
@RestController
@RequestMapping("/api/test")
public class TestController {
 
 @Autowired
 private EmailService emailService;
 
 @GetMapping("/email/welcome")
 public String testWelcomeEmail() {
     User testUser = new User();
     testUser.setName("Test User");
     testUser.setEmail("test@example.com");
     emailService.sendWelcomeEmail(testUser);
     return "Email sent!";
 }
}