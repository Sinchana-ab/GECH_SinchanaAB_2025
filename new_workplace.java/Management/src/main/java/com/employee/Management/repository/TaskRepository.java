package com.employee.Management.repository;

import com.employee.Management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedEmployeeId(Long employeeId);
    List<Task> findByStatus(String status);
    List<Task> findByAssignedEmployeeIdAndStatus(Long employeeId, String status);
}
