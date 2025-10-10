package com.springSecurity.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springSecurity.dto.UsersDTO;
import com.springSecurity.model.Users;
import com.springSecurity.service.UsersServices;


@Controller
public class HomeController {
	
	private UsersServices services;
	
	
	public HomeController(UsersServices services) {
		super();
		this.services = services;
	}

	@GetMapping({"/",""})
	public String home() {
		return "home";
	}
	
	@GetMapping({"/user-list"})
	public String about(Model model) {
		List<Users> users = services.getAllUsers();
		model.addAttribute("users",users);
		return "user_list";
	}
	@GetMapping({"/contact"})
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		UsersDTO dto = new UsersDTO();
		model.addAttribute("dto", dto);
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute UsersDTO dto) {
		services.saveStudent(dto);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}	
}
