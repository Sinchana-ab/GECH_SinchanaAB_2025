package com.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.security.dto.StudentDTO;
import com.spring.security.service.StudentService;


@Controller
public class HomeController {
	
	private StudentService studentService;
	
	
	public HomeController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	@GetMapping({"/"," "})
	public String home() {
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
	@GetMapping("/register")
	public String register(Model model) {
		StudentDTO studentDTO = new StudentDTO();
		model.addAttribute("studentDTO", studentDTO);
		return "register";
	}
	@PostMapping("/register")
	public String register(@ModelAttribute StudentDTO studentDTO, RedirectAttributes redirectAttributes) {
		studentService.storeStudent(studentDTO);
		redirectAttributes.addFlashAttribute("successMessage","Student saved successfull");
		//System.out.println(studentDTO);
		return "redirect:/";
	}
	
	@GetMapping("/std-details")
	public String stdDetails() {
		return "std-details";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	 @GetMapping("/logout-success")
	public String logoutPage() {
        return "logout";
    }
	
}
