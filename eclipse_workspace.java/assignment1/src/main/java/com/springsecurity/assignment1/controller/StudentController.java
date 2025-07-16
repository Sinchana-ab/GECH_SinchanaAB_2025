package com.springsecurity.assignment1.controller;

import com.springsecurity.assignment1.model.Student;
import com.springsecurity.assignment1.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 🟢 Show student list and form
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student()); // form backing bean
        return "students"; // students.html
    }

    // 🟢 Add or update student
    @PostMapping("/add")
    public String addOrUpdateStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // 🟡 Load student into form for editing
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit_student"; 
    }
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student); // it updates if ID is present
        return "redirect:/students";
    }



    // 🔴 Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
