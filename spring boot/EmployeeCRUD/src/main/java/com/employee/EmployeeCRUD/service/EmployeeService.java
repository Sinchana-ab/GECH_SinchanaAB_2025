package com.employee.EmployeeCRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.employee.EmployeeCRUD.dto.EmployeeDTO;
import com.employee.EmployeeCRUD.models.Employee;
import com.employee.EmployeeCRUD.repository.EmployeeRepository;

import lombok.Data;

@Service
@Data
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ✅ 1. Get list of all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ✅ 2. Save employee details (CREATE)
    public void saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPosition(employeeDTO.getPosition());
        employeeRepository.save(employee);
    }

    // ✅ 3. Get employee by ID (READ)
    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // ✅ 4. Update employee details (UPDATE)
    public void updateEmployee(EmployeeDTO employeeDTO, Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setPosition(employeeDTO.getPosition());
        employeeRepository.save(employee);
    }

    // ✅ 5. Delete employee by ID (DELETE)
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
