package com.construction.companyManagement.service;


import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construction.companyManagement.dto.ProjectDTO;
import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @Override
    public List<Project> getLatestProjects(int limit) {
        return projectRepo.findTop2ByOrderByIdDesc(); // You can update this logic if needed
    }

    @Override
    public void saveProject(ProjectDTO projectDTO) throws IOException {
        String fileName = projectDTO.getImage().getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/project-images");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(projectDTO.getImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        Project project = new Project();
        project.setTitle(projectDTO.getTitle());
        project.setLocation(projectDTO.getLocation());
        project.setDescription(projectDTO.getDescription());
        project.setImagePath("/project-images/" + fileName);

        projectRepo.save(project);
    }
}
