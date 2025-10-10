package com.employee.Management.controller;

import com.employee.Management.dto.EmployeeDTO;
import com.employee.Management.model.Employee;
import com.employee.Management.service.DepartmentService;
import com.employee.Management.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeManagementController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService; // Needed for dropdown in forms

    public EmployeeManagementController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // Display all employees
    @GetMapping
    public String manageEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("employeeDTO", new EmployeeDTO()); // For add form
        model.addAttribute("departments", departmentService.getAllDepartments()); // For department dropdown
        return "admin/manage_employees"; // Refers to src/main/resources/templates/admin/manage_employees.html
    }

    // Process adding a new employee
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDTO employeeDTO, RedirectAttributes redirectAttributes) {
        try {
            employeeService.createEmployee(employeeDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/employees";
    }

    // Show form to edit employee
    @GetMapping("/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        model.addAttribute("employeeDTO", new EmployeeDTO()); // For update form submission
        model.addAttribute("departments", departmentService.getAllDepartments()); // For department dropdown
        return "admin/edit_employee"; // Refers to src/main/resources/templates/admin/edit_employee.html
    }

    // Process updating an employee
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute EmployeeDTO employeeDTO, RedirectAttributes redirectAttributes) {
        try {
            employeeService.updateEmployee(id, employeeDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/employees";
    }

    // Delete an employee
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee: " + e.getMessage());
        }
        return "redirect:/admin/employees";
    }
}
