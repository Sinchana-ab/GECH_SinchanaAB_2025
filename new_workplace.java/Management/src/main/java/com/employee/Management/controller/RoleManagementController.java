package com.employee.Management.controller;

import com.employee.Management.dto.RoleDTO;
import com.employee.Management.model.Role;
import com.employee.Management.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/roles")
public class RoleManagementController {

    private final RoleService roleService;

    public RoleManagementController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Display all roles
    @GetMapping
    public String manageRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("roleDTO", new RoleDTO()); // For add form
        return "admin/manage_roles"; // Refers to src/main/resources/templates/admin/manage_roles.html
    }

    // Process adding a new role
    @PostMapping("/add")
    public String addRole(@ModelAttribute RoleDTO roleDTO, RedirectAttributes redirectAttributes) {
        try {
            roleService.createRole(roleDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Role added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/roles";
    }

    // Delete a role
    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteRole(id);
            redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting role: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }
}
