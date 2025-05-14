package com.construction.companyManagement.controller;

import com.construction.companyManagement.dto.ProjectDTO;

import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.model.TeamMember;
import com.construction.companyManagement.service.ProjectService;
import com.construction.companyManagement.service.TeamMemberService;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminAuthController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TeamMemberService teamMemberService;

//    @GetMapping("/admin/login")
//    public String loginPage() {
//        return "admin-login";
//    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }


    // Add Project
    @PostMapping("/admin/projects/add")
    public String addProject(@ModelAttribute ProjectDTO projectDTO) {
        try {
            projectService.saveProject(projectDTO);
            return "redirect:/admin/dashboard";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/dashboard?error=imageUpload";
        }
    }
    


    // Admin Dashboard
    @GetMapping("/admin/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Check if admin session exists
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login"; // Redirect to login if not authenticated
        }
        // Load dashboard data
        model.addAttribute("projects", projectService.getAllProjects());
        return "admin-dashboard"; // Render dashboard page
    }
    
    // All Projects
    @GetMapping("/admin/projects")
    public String showAllProjects(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        List<Project> allProjects = projectService.getAllProjects();
        model.addAttribute("projects", allProjects);

        List<TeamMember> members = teamMemberService.getAllTeamMembers();
        model.addAttribute("team", members);

        return "admin-dashboard";
    }

//    // Public Team Page
//    @GetMapping("/team")
//    public String showTeam(Model model) {
//        List<TeamMember> members = teamMemberService.getAllTeamMembers();
//        model.addAttribute("team", members);
//        return "team"; // You can reuse this section in index.html if preferred
//    }

    // Public Team Page
    @GetMapping("/team")
    public String showTeam(Model model) {
        List<TeamMember> members = teamMemberService.getAllTeamMembers();
        model.addAttribute("team", members);
        return "team"; // You can reuse this section in index.html if preferred
    }

}
