package com.construction.companyManagement.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.construction.companyManagement.dto.ProjectDTO;
import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.service.ProjectService;

@Controller
@RequestMapping("/admin/projects")
public class AdminProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/all-projects")
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "admin/all-projects";
    }



    @GetMapping("/add")
    public String showAddProjectForm(Model model) {
        model.addAttribute("projectDTO", new ProjectDTO());
        return "admin/projects-add";
    }

  

@PostMapping("/add")
public String addProject(
        @ModelAttribute ProjectDTO projectDTO  // ✅ Use DTO directly
) {
    try {
        projectService.saveProject(projectDTO);  // ✅ Calls the method you already wrote
    } catch (IOException e) {
        e.printStackTrace();
        return "redirect:/admin/projects/add?error";
    }

    return "redirect:/admin/dashboard";
}


@GetMapping("/edit/{id}")
public String showEditProjectForm(@PathVariable Long id, Model model) {
    Project project = projectService.getProjectById(id);
    if (project == null) {
        return "redirect:/admin/projects/all-projects";
    }
    model.addAttribute("project", project);
    return "admin/projects-edit";
}


    // Handle edit project submission
@PostMapping("/edit/{id}")
public String editProject(@PathVariable Long id,
                          @ModelAttribute Project updatedProject,
                          @RequestParam(value = "image", required = false) MultipartFile image) {
    try {
        projectService.updateProject(id, updatedProject, image);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "redirect:/admin/projects/all-projects";
}


@GetMapping("/delete/{id}")
public String deleteProject(@PathVariable Long id) {
    projectService.deleteProject(id);
    return "redirect:/admin/projects/all-projects";
}

}