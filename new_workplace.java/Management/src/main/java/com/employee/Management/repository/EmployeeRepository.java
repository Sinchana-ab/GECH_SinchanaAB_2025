package com.employee.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.Management.model.Employee;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByNameContainingIgnoreCase(String name);
}