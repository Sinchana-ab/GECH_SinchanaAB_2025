package com.employee.Management.controller;

import com.employee.Management.service.DepartmentService;
import com.employee.Management.service.EmployeeService;
import com.employee.Management.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final RoleService roleService;

    // Constructor injection for services
    public HomeController(EmployeeService employeeService, DepartmentService departmentService, RoleService roleService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleService = roleService;
    }
	
	@GetMapping({"/",""})
	public String home() {
		return "index";
	}

    @GetMapping("/employees")
    public String employeesPage(Model model) {
        // This line fetches ALL employees from the database via the service
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees"; // returns employees.html
    }

    @GetMapping("/departments")
    public String departmentsPage(Model model) {
        // This line fetches ALL departments from the database via the service
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments"; // returns departments.html
    }

    @GetMapping("/roles")
    public String rolesPage(Model model) {
        // This line fetches ALL roles from the database via the service
        model.addAttribute("roles", roleService.getAllRoles());
        return "roles"; // returns roles.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html
    }

}
