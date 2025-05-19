package com.construction.companyManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "index"; 
    }
    @GetMapping("/about")
    public String about() {
        return "about"; 
    }

//    @GetMapping("/services/residential")
//    public String residentialServices() {
//        return "services/residential"; 
//    }
//
//    @GetMapping("/services/commercial")
//    public String commercialServices() {
//        return "services/commercial"; 
//    }

    @GetMapping("/projects")
    public String projects() {

        return "projects"; 
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }
    @GetMapping("/team")
    public String Team() {
        return "team";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/admin/dashboard")
    public String dashoard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/login")
    public String showLoginPage(Model model) {
        return "admin/login";  // This just shows the login form
    }
   
    @PostMapping("/login-success")
    public String loginSuccess(HttpSession session) {
        session.setAttribute("admin", true); // Use a more meaningful value, like the admin's username
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/testimonials")
    public String testimonials() {
        return "testimonials";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/gallery")
    public String gallery() {
        return "gallery";
    } 
}
