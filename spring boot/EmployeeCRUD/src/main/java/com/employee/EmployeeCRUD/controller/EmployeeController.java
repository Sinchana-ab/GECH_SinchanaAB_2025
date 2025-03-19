package com.employee.EmployeeCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.employee.EmployeeCRUD.dto.EmployeeDTO;
import com.employee.EmployeeCRUD.models.Employee;
import com.employee.EmployeeCRUD.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    
    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees"; // This should match employees.html
    }
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
   

    // ✅ 2. Add employee form
    @GetMapping("/add-employee")
    public String addEmployee(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        model.addAttribute("employeeDTO", employeeDTO);
        return "add_employee"; // Renders add_employee.html
    }

    // ✅ 3. Save employee (POST)
    @PostMapping("/add-employee")
    public String saveEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
        employeeService.saveEmployee(employeeDTO);
        return "redirect:/employees";
    }

    // ✅ 4. Edit employee form
    @GetMapping("/edit-employee")
    public String getEmployee(@RequestParam Long id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setPosition(employee.getPosition());

        model.addAttribute("employeeDTO", employeeDTO); // Form fields
        model.addAttribute("employee", employee); // For ID
        return "edit_employee"; // Renders edit_employee.html
    }

    // ✅ 5. Update employee (POST)
    @PostMapping("/edit-employee")
    public String updateEmployee(@ModelAttribute EmployeeDTO employeeDTO, @RequestParam Long id) {
        employeeService.updateEmployee(employeeDTO, id);
        return "redirect:/employees";
    }

    // ✅ 6. Delete employee
    @GetMapping("/delete-employee")
    public String deleteEmployee(@RequestParam Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
