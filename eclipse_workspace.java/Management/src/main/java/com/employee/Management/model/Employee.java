package com.employee.Management.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;
private String email;

@ManyToOne
@JoinColumn(name = "department_id")
private Department department;

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "address_id", referencedColumnName = "id")
private Address address;

@ManyToMany
@JoinTable(
    name = "employee_project",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id")
)
private List<Project> projects;

// Getters and Setters

public Long getId() { return id; }

public void setId(Long id) { this.id = id; }

public String getName() { return name; }

public void setName(String name) { this.name = name; }

public String getEmail() { return email; }

public void setEmail(String email) { this.email = email; }

public Department getDepartment() { return department; }

public void setDepartment(Department department) { this.department = department; }

public Address getAddress() { return address; }

public void setAddress(Address address) { this.address = address; }

public List<Project> getProjects() { return projects; }

public void setProjects(List<Project> projects) { this.projects = projects; }
}

