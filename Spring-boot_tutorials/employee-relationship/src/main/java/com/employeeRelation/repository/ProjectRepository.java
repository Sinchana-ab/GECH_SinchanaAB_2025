package com.employeeRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeRelation.model.Projects;

public interface ProjectRepository extends JpaRepository<Projects, Long> {

}
