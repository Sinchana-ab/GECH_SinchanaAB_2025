package com.employee.Management.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Management.model.Department;
import com.employee.Management.model.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
List<Employee> findByDepartment(Department department);
}
