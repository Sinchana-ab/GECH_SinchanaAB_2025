package com.employee.Management.controller;

import com.employee.Management.model.User;
import com.employee.Management.model.Role;
import com.employee.Management.service.RoleService;
import com.employee.Management.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
public class UserManagementController {

    private final UserService userService;
    private final RoleService roleService;

    public UserManagementController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Display all users
    @GetMapping
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles()); // For role selection in edit form
        return "admin/manage_users"; // Refers to src/main/resources/templates/admin/manage_users.html
    }

    // Show form to edit user roles
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        // Get current role IDs for pre-selection in the form
        List<Long> currentRoleIds = user.getRoles().stream()
                                        .map(Role::getId)
                                        .collect(Collectors.toList());
        model.addAttribute("currentRoleIds", currentRoleIds);
        return "admin/edit_user"; // Refers to src/main/resources/templates/admin/edit_user.html
    }

    // Process update user roles
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserRoles(id, roleIds);
            redirectAttributes.addFlashAttribute("successMessage", "User roles updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Delete a user
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}

