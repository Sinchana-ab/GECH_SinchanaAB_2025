package com.employee.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.Management.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}