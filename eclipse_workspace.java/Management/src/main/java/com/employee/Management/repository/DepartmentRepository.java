package com.employee.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Management.model.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
Department findByName(String name);
}

