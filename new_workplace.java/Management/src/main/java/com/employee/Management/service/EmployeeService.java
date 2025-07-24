package com.employee.Management.service;

import com.employee.Management.model.Employee;
import com.employee.Management.model.Department;
import com.employee.Management.dto.EmployeeDTO;
import com.employee.Management.repository.EmployeeRepository;
import com.employee.Management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    public EmployeeService(EmployeeRepository employeeRepo, DepartmentRepository departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    // Create a new employee
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        // Associate with department
        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepo.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + employeeDTO.getDepartmentId()));
            employee.setDepartment(department);
        }
        return employeeRepo.save(employee);
    }

    // Update an existing employee
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        return employeeRepo.findById(id)
                .map(employee -> {
                    employee.setName(employeeDTO.getName());
                    employee.setEmail(employeeDTO.getEmail());
                    if (employeeDTO.getDepartmentId() != null) {
                        Department department = departmentRepo.findById(employeeDTO.getDepartmentId())
                                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + employeeDTO.getDepartmentId()));
                        employee.setDepartment(department);
                    } else {
                        employee.setDepartment(null); // Allow setting department to null
                    }
                    return employeeRepo.save(employee);
                })
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    // Find employees by department ID
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepo.findByDepartmentId(departmentId);
    }

    // Search employees by name
    public List<Employee> searchEmployees(String name) {
        return employeeRepo.findByNameContainingIgnoreCase(name);
    }
}
