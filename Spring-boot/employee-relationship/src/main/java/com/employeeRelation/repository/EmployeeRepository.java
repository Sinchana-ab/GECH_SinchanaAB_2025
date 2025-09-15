package com.employeeRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeRelation.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
