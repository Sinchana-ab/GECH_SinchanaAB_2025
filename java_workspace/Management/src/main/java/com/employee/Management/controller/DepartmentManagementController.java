package com.employee.Management.controller;

import com.employee.Management.dto.DepartmentDTO;
import com.employee.Management.model.Department;
import com.employee.Management.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentManagementController {

    private final DepartmentService departmentService;

    public DepartmentManagementController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String manageDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("departmentDTO", new DepartmentDTO()); 
        return "admin/manage_departments"; 
    }

   
    @PostMapping("/add")
    public String addDepartment(@ModelAttribute DepartmentDTO departmentDTO, RedirectAttributes redirectAttributes) {
        try {
            departmentService.createDepartment(departmentDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Department added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/departments";
    }

    // Show form to edit department
    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department Id:" + id));
        model.addAttribute("department", department);
        model.addAttribute("departmentDTO", new DepartmentDTO()); // For update form submission
        return "admin/edit_department"; // Refers to src/main/resources/templates/admin/edit_department.html
    }

    // Process updating a department
    @PostMapping("/update/{id}")
    public String updateDepartment(@PathVariable Long id, @ModelAttribute DepartmentDTO departmentDTO, RedirectAttributes redirectAttributes) {
        try {
            departmentService.updateDepartment(id, departmentDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Department updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/departments";
    }

    // Delete a department
    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting department: " + e.getMessage());
        }
        return "redirect:/admin/departments";
    }
}
