package com.employee.Management.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.employee.Management.model.Employee;
import com.employee.Management.repository.DepartmentRepository;
import com.employee.Management.repository.EmployeeRepository;
import com.employee.Management.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

@Autowired
private EmployeeRepository employeeRepository;

@Autowired
private DepartmentRepository departmentRepository;

@Autowired
private EmployeeService employeeService;

@GetMapping({"/", ""})
public String listEmployees(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    // You can add admin filtering logic here like your NotesController
    List<Employee> employees = employeeRepository.findAll();
    model.addAttribute("employees", employees);
    return "employees"; // employees.html
}

@GetMapping("/add")
public String addEmployeeForm(Model model) {
    model.addAttribute("employeeDTO", new EmployeeDTO());
    model.addAttribute("departments", departmentRepository.findAll());
    return "add-employee"; // add-employee.html
}

@PostMapping("/add")
public String addEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
    if (result.hasErrors()) {
        model.addAttribute("departments", departmentRepository.findAll());
        return "add-employee";
    }

    employeeService.saveEmployee(employeeDTO);
    redirectAttributes.addFlashAttribute("success", "Employee added successfully");
    return "redirect:/employees";
}

@GetMapping("/edit/{id}")
public String editEmployeeForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    Optional<Employee> optionalEmployee = employeeRepository.findById(id);
    if (optionalEmployee.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "Employee not found");
        return "redirect:/employees";
    }

    Employee employee = optionalEmployee.get();
    EmployeeDTO dto = new EmployeeDTO();
    dto.setName(employee.getName());
    dto.setEmail(employee.getEmail());
    dto.setDepartmentId(employee.getDepartment().getId());

    model.addAttribute("employeeDTO", dto);
    model.addAttribute("employee", employee);
    model.addAttribute("departments", departmentRepository.findAll());

    return "edit-employee"; // edit-employee.html
}

@PostMapping("/edit/{id}")
public String updateEmployee(@PathVariable Long id,
                             @Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
    if (result.hasErrors()) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("employee", employeeRepository.findById(id).orElse(null));
        return "edit-employee";
    }

    employeeService.updateEmployee(id, employeeDTO);
    redirectAttributes.addFlashAttribute("success", "Employee updated successfully");
    return "redirect:/employees";
}
}