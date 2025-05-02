package com.gecCRUD.EmployeeCRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gecCRUD.EmployeeCRUD.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public Employee findByEmail(String email);
	boolean existsByEmail(String email);

}
