package com.employee.Management.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String projectName;

@ManyToMany(mappedBy = "projects")
private List<Employee> employees;

// Getters and Setters

public Long getId() { return id; }

public void setId(Long id) { this.id = id; }

public String getProjectName() { return projectName; }

public void setProjectName(String projectName) { this.projectName = projectName; }

public List<Employee> getEmployees() { return employees; }

public void setEmployees(List<Employee> employees) { this.employees = employees; }
}