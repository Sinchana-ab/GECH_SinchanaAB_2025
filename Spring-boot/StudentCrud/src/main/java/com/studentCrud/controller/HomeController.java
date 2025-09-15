package com.studentCrud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.studentCrud.dto.StudentDTO;
import com.studentCrud.model.Student;
import com.studentCrud.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class HomeController {
	
	private StudentService studentService;
	
	
	public HomeController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}


	@GetMapping({"","/"})
	public String home(Model model) {
		List<Student> students = studentService.getAllStudents();
		model.addAttribute("students",students);
		return "home";
	}
	
	@GetMapping("/add-student")
	public String addStudent(Model model) {
		model.addAttribute("stdudentDTO", new StudentDTO());
		return "add-student";
	}
	
	@PostMapping("/add-student")
	public String addStudent(@ModelAttribute StudentDTO studentDTO, RedirectAttributes attributes, Model model,BindingResult bindingResult) {
		studentService.saveStudent(studentDTO);
		return "redirect:/";
	}
	
	@PostMapping("/edit-student")
	public String getStudent(@RequestParam Long Id, Model model) {
		
		
		return "edit-student";
	}
		
}
