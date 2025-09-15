package com.spring.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.web.UserService;
import com.spring.web.model.User;

@Controller
public class HomeController {
	
	UserService service;
	
	
	public HomeController(UserService service) {
		super();
		this.service = service;
	}
	@GetMapping({"/",""})
	public String home(Model model) {
		List<User> users = service.getAllUser();
		model.addAttribute("users",users);
		return "home";
	}
	@GetMapping({"/about"})
	public String about() {
		return "about";
	}
	@GetMapping({"/contact"})
	public String contact() {
		return "contact";
	}
	
}
