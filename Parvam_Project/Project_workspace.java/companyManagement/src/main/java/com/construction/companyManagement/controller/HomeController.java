package com.construction.companyManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.construction.companyManagement.dto.ContactMessageDTO;
import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.service.ProjectService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/projects")
    public String showProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();

        // Group projects into sublists of 3
        List<List<Project>> projectGroups = new ArrayList<>();
        for (int i = 0; i < projects.size(); i += 3) {
            projectGroups.add(projects.subList(i, Math.min(i + 3, projects.size())));
        }

        model.addAttribute("projectGroups", projectGroups);
        return "projects";
    }
    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return "redirect:/projects"; 
        }
        model.addAttribute("project", project);
        return "project-details"; 
    }


    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/team")
    public String team() {
        return "team";
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

    @GetMapping("/form")
    public String showContactForm(Model model) {
        model.addAttribute("contactMessage", new ContactMessageDTO());
        return "contact";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/login")
    public String showLoginPage(Model model) {
        return "admin/login";
    }

    @PostMapping("/login-success")
    public String loginSuccess(HttpSession session) {
        session.setAttribute("admin", true);
        return "redirect:/admin/dashboard";
    }
}