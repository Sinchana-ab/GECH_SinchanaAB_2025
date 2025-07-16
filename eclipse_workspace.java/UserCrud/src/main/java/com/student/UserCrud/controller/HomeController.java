package com.student.UserCrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.student.UserCrud.dto.StudentDTO;
import com.student.UserCrud.dto.UserDTO;
import com.student.UserCrud.repository.StudentRepository;
import com.student.UserCrud.service.StudentService;
import com.student.UserCrud.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class HomeController {

   
	
	private StudentService studentService;
	private UserService userService;
	
	

	public HomeController(StudentService studentService, UserService userService) {
		super();
		this.studentService = studentService;
		this.userService = userService;
	}

	@GetMapping({"/",""})
	public String home() {
		return "home";
	}
	
	@GetMapping({"/about"})
	public String about() {
		return "about";
	}
	@GetMapping({"/login"})
	public String login() {
		return "login";
	}
	@GetMapping({"/register"})
	public String register() {
		return "register";
	}
	@PostMapping("/register")
	public String register(UserDTO userDTO,RedirectAttributes attributes) {
		userService.saveUser(userDTO);
		attributes.addFlashAttribute("success", "User successfully registered");
		return "redirect:/";
	}
	@GetMapping("/studentList")
	public String studentList() {
		return "studentList";
	}
	
	@PostMapping("/addStudent")
	public String addStudent(StudentDTO studentDTO, RedirectAttributes attributes) {
		studentService.saveStudent(studentDTO);
		attributes.addFlashAttribute("success1", "Student successfully added");
		return "redirect:/studentList";
	}
	
}
