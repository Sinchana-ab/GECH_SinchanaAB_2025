package com.employee.Management.service;

import com.employee.Management.model.User;
import com.employee.Management.model.Role;
import com.employee.Management.repository.UserRepository;
import com.employee.Management.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    // Update user's roles
    @Transactional
    public User updateUserRoles(Long userId, List<Long> roleIds) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Set<Role> newRoles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                roleRepo.findById(roleId).ifPresent(newRoles::add);
            }
        }
        user.setRoles(newRoles);
        return userRepo.save(user);
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // Create a new user (can be used by admin for direct user creation)
    public User createUser(String username, String password, List<Long> roleIds) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                roleRepo.findById(roleId).ifPresent(roles::add);
            }
        }
        newUser.setRoles(roles);
        return userRepo.save(newUser);
    }
}
