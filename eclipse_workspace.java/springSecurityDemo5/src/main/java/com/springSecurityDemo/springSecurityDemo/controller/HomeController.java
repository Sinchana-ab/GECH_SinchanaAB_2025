package com.springSecurityDemo.springSecurityDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springSecurityDemo.springSecurityDemo.dto.StudentDTO;
import com.springSecurityDemo.springSecurityDemo.service.StudentService;

@Controller
public class HomeController {

	private StudentService studentService;
	
	
	
	public HomeController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	@GetMapping({ "/", "" })
	public String home() {
		return "home";
	}
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	@GetMapping("/register")
	public String register(Model model) {
		StudentDTO studentDTO = new StudentDTO();
		model.addAttribute("studentDTO",studentDTO);
		return "register";
	}
	@PostMapping("/register")
	public String register(@ModelAttribute StudentDTO studentDTO, RedirectAttributes redirectAttributes, Model model) {
		try {
			studentService.storeStudent(studentDTO);
			redirectAttributes.addFlashAttribute("success","Student saved succesfully");
		} catch (Exception e) {
			model.addAttribute("error","Failed to send email");
			return "registration";
		}
		
		return "redirect:/login";
	}
	@GetMapping("/std-details")
	public String std(){
		return "std-details";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}	
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
}
