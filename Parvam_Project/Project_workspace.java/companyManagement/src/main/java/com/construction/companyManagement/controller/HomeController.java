package com.construction.companyManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // loads src/main/resources/templates/index.html
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/blogs")
    public String blogs() {
        return "blogs";
    }
}
