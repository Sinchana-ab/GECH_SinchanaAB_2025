package com.springsecurity.assignment1.service;

import com.springsecurity.assignment1.model.Student;
import com.springsecurity.assignment1.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // List all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Save or update student
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    // Delete student by ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
}
