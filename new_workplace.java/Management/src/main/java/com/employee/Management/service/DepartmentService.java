package com.employee.Management.service;

import com.employee.Management.model.Department;
import com.employee.Management.dto.DepartmentDTO;
import com.employee.Management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepo;

    public DepartmentService(DepartmentRepository departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepo.findById(id);
    }

    public Department createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        return departmentRepo.save(department);
    }

    public Department updateDepartment(Long id, DepartmentDTO departmentDTO) {
        return departmentRepo.findById(id)
                .map(department -> {
                    department.setName(departmentDTO.getName());
                    return departmentRepo.save(department);
                })
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));
    }

    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }
}
