package com.employee.Management.service;

import com.employee.Management.model.Role;
import com.employee.Management.dto.RoleDTO;
import com.employee.Management.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    // Get all roles
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    // Get role by ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepo.findById(id);
    }

    // Create a new role
    public Role createRole(RoleDTO roleDTO) {
        if (roleRepo.findByName(roleDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Role with name '" + roleDTO.getName() + "' already exists.");
        }
        Role role = new Role();
        role.setName(roleDTO.getName());
        return roleRepo.save(role);
    }

    // Delete a role
    public void deleteRole(Long id) {
        if (!roleRepo.existsById(id)) {
            throw new IllegalArgumentException("Role not found with ID: " + id);
        }
        // Consider logic to reassign users from this role before deleting
        // For simplicity, we'll just delete for now.
        roleRepo.deleteById(id);
    }
}
