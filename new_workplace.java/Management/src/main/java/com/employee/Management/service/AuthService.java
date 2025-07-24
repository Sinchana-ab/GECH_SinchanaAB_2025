package com.employee.Management.service;

import com.employee.Management.model.User;
import com.employee.Management.model.Employee; // Import Employee
import com.employee.Management.dto.UserDTO;
import com.employee.Management.model.Role;
import com.employee.Management.repository.UserRepository;
import com.employee.Management.repository.RoleRepository;
import com.employee.Management.repository.EmployeeRepository; // Import EmployeeRepository
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional; // Import Transactional

import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final EmployeeRepository employeeRepo; // Inject EmployeeRepository
    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepo, RoleRepository roleRepo, EmployeeRepository employeeRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.employeeRepo = employeeRepo; // Initialize EmployeeRepository
        this.encoder = encoder;
    }

    @Transactional // Ensure the whole operation is transactional
    public void registerUser(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));

        Set<Role> assignedRoles = new HashSet<>();
        Optional<Role> userRole = roleRepo.findByName("USER");

        // Always assign the "USER" role during registration, regardless of selection
        if (userRole.isPresent()) {
            assignedRoles.add(userRole.get());
        } else {
            System.err.println("Error: 'USER' role not found. Please ensure it's created on startup.");
            // Consider throwing an exception or handling this case more robustly
        }
        user.setRoles(assignedRoles);

        // Create a new Employee record and link it to the User
        Employee employee = new Employee();
        employee.setName(dto.getUsername()); // Default employee name to username
        employee.setEmail(dto.getEmail()); // Use email from DTO for employee
        employee.setUser(user); // Link employee to user
        
        user.setEmployee(employee); // Link user to employee (bidirectional)

        userRepo.save(user); // Saving user will cascade persist the employee due to CascadeType.ALL
    }
}
