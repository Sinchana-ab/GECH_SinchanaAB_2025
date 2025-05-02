package com.construction.companyManagement.controller;

import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminProjectController {

    @Autowired
    private ProjectRepository projectRepo;

    @GetMapping("/admin/projects")
    public String viewProjects(Model model) {
        List<Project> projects = projectRepo.findAll();
        model.addAttribute("projects", projects);
        return "admin-projects"; // loads admin-projects.html
    }
}
