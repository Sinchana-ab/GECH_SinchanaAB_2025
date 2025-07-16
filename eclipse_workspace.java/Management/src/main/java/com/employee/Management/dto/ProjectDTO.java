package com.employee.Management.dto;
import jakarta.validation.constraints.NotBlank;

public class ProjectDTO {

@NotBlank(message = "Project name is required")
private String projectName;

// Getters and Setters

public String getProjectName() {
    return projectName;
}

public void setProjectName(String projectName) {
    this.projectName = projectName;
}
}
