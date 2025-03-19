package com.employee.EmployeeCRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.EmployeeCRUD.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>  {
	
}
