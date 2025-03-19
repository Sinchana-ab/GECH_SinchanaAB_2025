package com.geccrud.SpringBootCRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.geccrud.SpringBootCRUD.dto.StudentDTO;
import com.geccrud.SpringBootCRUD.models.Student;
import com.geccrud.SpringBootCRUD.service.StudentService;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {
	
	private final StudentService studentService;
//	@Service
//	@SpringBootApplication
	@Autowired
	public HomeController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping({"","/"})
	public String getAllStudents(Model model) {
		List<Student> students =  studentService.getAllStudents();
		model.addAttribute("students",students);
		return "students";
	}
	
	@GetMapping("/add-student")
	public String addStudent(Model model) {
		StudentDTO studentDTO = new StudentDTO();
		model.addAttribute("studentDTO",studentDTO);
		return "add_student";
	}
	@PostMapping("/add-student")
	public String saveStudent(@ModelAttribute StudentDTO studentDTO) {
		studentService.saveStudent(studentDTO);
		return "redirect:/";
	}
	@GetMapping("/edit-student")
	public String getStudent(@RequestParam Long id, Model model) {
	    Student student = studentService.getStudent(id);
	    StudentDTO studentDTO = new StudentDTO();
	    studentDTO.setfName(student.getfName());
	    studentDTO.setlName(student.getlName());
	    studentDTO.setEmail(student.getEmail());
	    studentDTO.setPhone(student.getPhone());
	    studentDTO.setAddress(student.getAddress());
	    model.addAttribute("studentDTO", studentDTO); // form fields
	    model.addAttribute("student", student); // for id
	    return "edit_student"; // template name should match exactly
	}

	@PostMapping("/edit-student")
	public String updateStudent(@ModelAttribute StudentDTO studentDTO, @RequestParam Long id) {
	    studentService.updateStudent(studentDTO, id);
	    return "redirect:/";
	}

	
	@GetMapping("/delete-student")
    public String deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);
        return "redirect:/"; 
    }

}
