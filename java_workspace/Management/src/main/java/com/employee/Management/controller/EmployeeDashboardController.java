package com.employee.Management.controller;

import com.employee.Management.model.Employee;
import com.employee.Management.model.Task;
import com.employee.Management.model.User; // Import User model
import com.employee.Management.service.EmployeeService;
import com.employee.Management.service.TaskService;
import com.employee.Management.service.CustomUserDetails; // Import CustomUserDetails
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeDashboardController {

    private final EmployeeService employeeService; // Still needed for other employee-related operations potentially
    private final TaskService taskService;

    public EmployeeDashboardController(EmployeeService employeeService, TaskService taskService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    // Helper method to get the logged-in User and their linked Employee
    private Optional<Employee> getLoggedInEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User loggedInUser = userDetails.getUser(); // Get the User entity from CustomUserDetails
            return Optional.ofNullable(loggedInUser.getEmployee()); // Return the linked Employee
        }
        return Optional.empty();
    }

    @GetMapping("/dashboard")
    public String employeeDashboard(Model model) {
        Optional<Employee> currentEmployeeOptional = getLoggedInEmployee();
        
        if (currentEmployeeOptional.isPresent()) {
            Employee currentEmployee = currentEmployeeOptional.get();
            List<Task> assignedTasks = taskService.getTasksByEmployeeId(currentEmployee.getId());
            model.addAttribute("assignedTasks", assignedTasks);
            model.addAttribute("employee", currentEmployee); // Pass employee details
        } else {
            model.addAttribute("errorMessage", "Employee profile not found for your account. Please contact support.");
        }

        return "employee_dashboard";
    }

    @GetMapping("/my-profile")
    public String myProfile(Model model) {
        Optional<Employee> employeeOptional = getLoggedInEmployee();
        
        if (employeeOptional.isPresent()) {
            model.addAttribute("employee", employeeOptional.get());
        } else {
            model.addAttribute("errorMessage", "Employee profile not found for your account. Please contact support.");
        }
        
        return "my_profile";
    }

    @GetMapping("/leave-requests")
    public String leaveRequests(Model model) {
        // In a real application, fetch leave requests for the logged-in employee
        // model.addAttribute("leaveRequests", leaveService.getLeaveRequestsForEmployee(currentEmployeeId));
        return "leave_requests";
    }

    @PostMapping("/leave-requests/submit")
    public String submitLeaveRequest(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "Leave request submitted successfully!");
        return "redirect:/employee/leave-requests";
    }
}
