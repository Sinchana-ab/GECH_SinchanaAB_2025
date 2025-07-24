package com.employee.Management.service;

import com.employee.Management.model.Task;
import com.employee.Management.model.Employee;
import com.employee.Management.dto.TaskDTO;
import com.employee.Management.repository.TaskRepository;
import com.employee.Management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    // Get all tasks (for admin view)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get tasks assigned to a specific employee
    public List<Task> getTasksByEmployeeId(Long employeeId) {
        return taskRepository.findByAssignedEmployeeId(employeeId);
    }

    // Get a single task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Create a new task
    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(taskDTO.getStatus() != null ? taskDTO.getStatus() : "Pending"); // Default to Pending

        if (taskDTO.getAssignedEmployeeId() != null) {
            Employee employee = employeeRepository.findById(taskDTO.getAssignedEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Assigned Employee not found with ID: " + taskDTO.getAssignedEmployeeId()));
            task.setAssignedEmployee(employee);
        } else {
            // Handle case where no employee is assigned (e.g., general task, or throw error)
            throw new IllegalArgumentException("Task must be assigned to an employee.");
        }
        return taskRepository.save(task);
    }

    // Update an existing task
    public Task updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDTO.getTitle());
                    task.setDescription(taskDTO.getDescription());
                    task.setDueDate(taskDTO.getDueDate());
                    task.setStatus(taskDTO.getStatus());

                    if (taskDTO.getAssignedEmployeeId() != null) {
                        Employee employee = employeeRepository.findById(taskDTO.getAssignedEmployeeId())
                                .orElseThrow(() -> new IllegalArgumentException("Assigned Employee not found with ID: " + taskDTO.getAssignedEmployeeId()));
                        task.setAssignedEmployee(employee);
                    } else {
                        task.setAssignedEmployee(null); // Allow unassigning
                    }
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    // Delete a task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Update task status (e.g., for employee to mark as complete)
    public Task updateTaskStatus(Long taskId, String newStatus) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setStatus(newStatus);
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
    }
}
