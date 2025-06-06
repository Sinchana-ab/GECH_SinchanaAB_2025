package com.role_based_authentication_notes.roleBasedNotesTaker.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.OnCreate;
import com.role_based_authentication_notes.roleBasedNotesTaker.dto.OnUpdate;
import com.role_based_authentication_notes.roleBasedNotesTaker.dto.UserDTO;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Role;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.RoleRepository;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.UserRepository;
import com.role_based_authentication_notes.roleBasedNotesTaker.services.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private UserRepository userRepository;
	private AuthService authService;
	private RoleRepository roleRepository;
	
	
	public UserController(UserRepository userRepository, AuthService authService, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.authService = authService;
		this.roleRepository = roleRepository;
	}
	
	@GetMapping({"/",""})
	public String users(Model model) {
		List<User> users= userRepository.findAll();
		model.addAttribute("users",users);
	    return "users"; // Returns the view named "users.html"
	}


	@PostMapping("/register")
	public String register(@Validated(OnCreate.class) @ModelAttribute UserDTO userDTO, BindingResult result, RedirectAttributes attributes) {
		Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
		if(user.isPresent()) {
			result.addError(
					new FieldError("UserDTO", "email", "Email is already registred")
					);
		}
		if(result.hasErrors()) {
			return "registration";
		}
		authService.saveUser(userDTO);
		attributes.addFlashAttribute("success", "Registration successfull. Please login");
		return "redirect:/login";
	}
	
	@GetMapping("/add-user")
	public String addUser(Model model) {
		List<Role> roles =  roleRepository.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("userDTO", new UserDTO());
	    return "add-user"; // Returns the view named "users.html"
	}
	
	@PostMapping("/add-user")
	public String storeUser(@Validated(OnCreate.class) @ModelAttribute UserDTO userDTO, BindingResult result, Model model, RedirectAttributes attributes) {
		if(userDTO.getRoles().isEmpty()) {
			result.addError(
					new FieldError("UserDTO", "roles", "At least one role must be selected.")
					);
		}
		Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
		if(user.isPresent()) {
			result.addError(
					new FieldError("UserDTO", "email", "Email is already registred")
					);
		}
		
		if(result.hasErrors()) {
			model.addAttribute("roles",roleRepository.findAll());
			return "add-user";
		}
		System.out.println(userDTO);
		authService.saveUser(userDTO);
		attributes.addFlashAttribute("success", "User saved successfully");
		return "redirect:/users";
	}
	
	@GetMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable Long id, RedirectAttributes attributes) {
		Optional<User> user =  userRepository.findById(id);
		if(user.isEmpty()) {
			attributes.addFlashAttribute("error","User with the is not present");
			return"redirect:/users";
		}
		for(var role :user.get().getRoles()) {
			role.getUsers().remove(user.get());
		}
		try {
			userRepository.delete(user.get());
		} catch (Exception e) {
			attributes.addFlashAttribute("error",e.getMessage());
	        return "redirect:/users"; 
		}
		attributes.addFlashAttribute("success","User deleted successfully");
		return "redirect:/users";
	}
	
	@GetMapping("/edit-user/{id}")
	public String editUser(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		UserDTO userDTO = new UserDTO();
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setAddress(user.getAddress().getAddress());
		userDTO.setRoles(user.getRoles().stream().map(role-> role.getId()).collect(Collectors.toSet()));
		
		model.addAttribute("user",user);
		model.addAttribute("userDTO", userDTO);
	    model.addAttribute("roles", roleRepository.findAll()); // to populate roles dropdown
		return "edit-user";
	}
	
	@PostMapping("/edit-user/{id}")
	public String updateUser(@PathVariable Long id ,@Validated(OnUpdate.class) @ModelAttribute UserDTO userDTO, BindingResult result, Model model, RedirectAttributes attributes) {
		if(userDTO.getRoles().isEmpty()) {
			result.addError(
					new FieldError("UserDTO", "roles", "At least one role must be selected.")
					);
		}
		Optional<User> old_user = userRepository.findByEmail(userDTO.getEmail());
		if(old_user.isPresent() && !old_user.get().getId().equals(id)) {
			result.addError(
					new FieldError("UserDTO", "email", "Email is already registred")
					);
		}
		
		if(result.hasErrors()) {
			User user = userRepository.findById(id).get();
			model.addAttribute("user",user);
		    model.addAttribute("roles", roleRepository.findAll()); // to populate roles dropdown
			return "edit-user";
		}
		authService.updateUser(userDTO,id);
		attributes.addFlashAttribute("success","User updated successfully");
		return "redirect:/users";
	}
	
}
