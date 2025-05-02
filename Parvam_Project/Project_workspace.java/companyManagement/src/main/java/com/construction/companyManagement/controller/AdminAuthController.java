package com.construction.companyManagement.controller;

import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.repository.ProjectRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminAuthController {

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        if (username.equals("admin") && password.equals("admin123")) {
            session.setAttribute("admin", "true");
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin-login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("projects", projectRepo.findAll()); // send data to dashboard
        return "admin-dashboard";
    }

    @PostMapping("/admin/projects/add")
    public String addProject(@RequestParam String title,
                             @RequestParam String location,
                             @RequestParam String description) {
        Project project = new Project();
        project.setTitle(title);
        project.setLocation(location);
        project.setDescription(description);

        projectRepo.save(project);
        return "redirect:/admin/dashboard";
    }
}
