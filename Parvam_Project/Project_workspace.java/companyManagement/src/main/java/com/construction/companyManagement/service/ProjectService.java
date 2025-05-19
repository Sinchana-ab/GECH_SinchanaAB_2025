package com.construction.companyManagement.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.repository.ProjectRepository;
import com.construction.companyManagement.dto.ProjectDTO;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    public void saveProject(ProjectDTO dto) throws IOException {
        Project project = new Project();
        project.setTitle(dto.getTitle());
        project.setLocation(dto.getLocation());
        project.setDescription(dto.getDescription());

        String fileName = dto.getImage().getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/uploads/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Files.copy(dto.getImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        project.setImagePath(fileName);
        projectRepo.save(project);
    }

    public List<Project> getTopProjects() {
        return projectRepo.findTop2ByOrderByIdDesc();
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepo.findById(id).orElse(null);
    }

    public void updateProject(Long id, Project updated, MultipartFile image) throws IOException {
        Project existing = projectRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setTitle(updated.getTitle());
            existing.setLocation(updated.getLocation());
            existing.setDescription(updated.getDescription());

            if (image != null && !image.isEmpty()) {
                String fileName = image.getOriginalFilename();
                Path uploadPath = Paths.get("src/main/resources/static/uploads/");
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
                Files.copy(image.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                existing.setImagePath(fileName);
            }

            projectRepo.save(existing);
        }
    }

    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }

}
