package com.construction.companyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construction.companyManagement.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
