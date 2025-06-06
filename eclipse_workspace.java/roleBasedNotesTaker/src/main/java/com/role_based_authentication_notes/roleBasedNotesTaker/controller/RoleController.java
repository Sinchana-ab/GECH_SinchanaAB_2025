package com.role_based_authentication_notes.roleBasedNotesTaker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.RoleDTO;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Role;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.RoleRepository;
import com.role_based_authentication_notes.roleBasedNotesTaker.services.RoleService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {
	
	private RoleRepository roleRepository;
	private RoleService roleService;
	
	
	public RoleController(RoleRepository roleRepository, RoleService roleService) {
		super();
		this.roleRepository = roleRepository;
		this.roleService = roleService;
	}

	@GetMapping({"/",""})
	public String roles(Model model) {
		List<Role> roles = roleRepository.findAll();
		model.addAttribute("roles",roles);
	    return "roles"; // Returns the view named "roles.html"
	}
	
	@GetMapping("/add-role")
	public String role(Model model ) {
		model.addAttribute("roleDTO",new RoleDTO());
		return "add-role";
	}
	
	@PostMapping("/add-role")
	public String role(@Valid @ModelAttribute RoleDTO roleDTO, BindingResult result, RedirectAttributes attributes) {
		System.out.println(roleDTO.getName());
		Optional<Role> role =  roleRepository.findByName(roleDTO.getName().toUpperCase());
		if(role.isPresent()) {
			result.addError(
					new FieldError("RoleDTO", "name", "Role with given name is already present.")
					);
		}
		if(result.hasErrors()) {
			return "add-role";
		}
		roleService.saveRole(roleDTO);
		attributes.addFlashAttribute("success","Role Saved Successfully");
		return "redirect:/roles";
	}
	
	@GetMapping("/edit-role/{id}")
	public String editRole(@PathVariable Long id, Model model, RedirectAttributes attributes) {
		Optional<Role> role =  roleRepository.findById(id);
		if(role.isEmpty()) {
			attributes.addAttribute("error","Role with given id notpresent");
			return "redirect:/roles";
		}
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setName(role.get().getName());
		model.addAttribute("role",role.get());
		model.addAttribute("roleDTO",roleDTO);
		return "edit-role";
	}
	
	@PostMapping("/edit-role/{id}")
	public String editRole(@Valid @ModelAttribute RoleDTO roleDTO, @PathVariable Long id, BindingResult result, RedirectAttributes attributes, Model model) {
		Optional<Role> role = roleRepository.findByName(roleDTO.getName().toUpperCase());
		if(role.isPresent() && !role.get().getId().equals(id)) {
			System.out.println(role.get()+" "+id);
			result.addError(
					new FieldError("RoleDTO", "name", "Role with given name is already present")
					);
		}
		if(result.hasErrors()) {
			Optional<Role> existing_role = roleRepository.findById(id);
			model.addAttribute("role",existing_role.get());
			return "edit-role";
		}
		try {
			roleService.updateRole(roleDTO, id);
		} catch (EntityNotFoundException ex) {
			attributes.addFlashAttribute("error",ex.getMessage());
	        return "redirect:/roles"; 
	    }
		attributes.addFlashAttribute("success","Role Updated Successfully");
		return "redirect:/roles";
	}
	
	@GetMapping("/delete-role/{id}")
	public String deleteRole(@PathVariable Long id, RedirectAttributes attributes) {
		 Optional<Role> role = roleRepository.findById(id);
		 if(role.isPresent()) {
			 try {
				 	roleService.deleteRole(id);
				} 
			 catch (EntityNotFoundException ex) {
					attributes.addFlashAttribute("error",ex.getMessage());
			        return "redirect:/roles"; 
			 }
		 }
		 else {
			attributes.addFlashAttribute("error","Role with id is not present");
			return "redirect:/roles";
		 }
		attributes.addFlashAttribute("success","Role deleted successfully.");
		return "redirect:/roles";
	}
}