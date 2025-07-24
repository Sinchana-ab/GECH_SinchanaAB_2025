package com.employee.Management.controller;

import com.employee.Management.dto.TaskDTO;
import com.employee.Management.model.Task;
import com.employee.Management.service.TaskService;
import com.employee.Management.service.EmployeeService; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/tasks")
public class TaskManagementController {

    private final TaskService taskService;
    private final EmployeeService employeeService; // Inject EmployeeService

    public TaskManagementController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    // Display all tasks
    @GetMapping
    public String manageTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("taskDTO", new TaskDTO()); // For add form
        model.addAttribute("employees", employeeService.getAllEmployees()); // For employee dropdown
        return "admin/manage_tasks"; // Refers to src/main/resources/templates/admin/manage_tasks.html
    }

    // Process adding a new task
    @PostMapping("/add")
    public String addTask(@ModelAttribute TaskDTO taskDTO, RedirectAttributes redirectAttributes) {
        try {
            taskService.createTask(taskDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Task added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/tasks";
    }

    // Show form to edit task
    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);
        model.addAttribute("taskDTO", new TaskDTO()); // For update form submission
        model.addAttribute("employees", employeeService.getAllEmployees()); // For employee dropdown
        model.addAttribute("statuses", new String[]{"Pending", "In Progress", "Completed", "Cancelled"}); // Possible statuses
        return "admin/edit_task"; // Refers to src/main/resources/templates/admin/edit_task.html
    }

    // Process updating a task
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute TaskDTO taskDTO, RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTask(id, taskDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/tasks";
    }

    // Delete a task
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting task: " + e.getMessage());
        }
        return "redirect:/admin/tasks";
    }
}
