package com.employeeRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeRelation.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
