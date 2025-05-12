package com.construction.companyManagement.service;


import java.io.IOException;
import java.util.List;
import com.construction.companyManagement.model.Project;
import com.construction.companyManagement.dto.ProjectDTO;

public interface ProjectService {
    List<Project> getAllProjects();
    List<Project> getLatestProjects(int limit);
    void saveProject(ProjectDTO projectDTO) throws IOException;

}
