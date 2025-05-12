package com.construction.companyManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
 // ProjectController.java
  //  @GetMapping("/admin/projects/edit/{id}")
  //  public String editProjectForm(@PathVariable Long id, Model model) { /* populate form */ }

  //  @PostMapping("/admin/projects/update")
   // public String updateProject(ProjectDTO dto) { /* handle update */ }

 //   @GetMapping("/admin/projects/delete/{id}")
 //   public String deleteProject(@PathVariable Long id) { /* handle deletion */ }

}
