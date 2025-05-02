package com.spring.security.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.dto.StudentDTO;
import com.spring.security.models.Student;
import com.spring.security.repository.StudentRepository;

@Service
public class StudentService {

    private PasswordEncoder passwordEncoder;
	private StudentRepository studentRepo;
	
	public StudentService(StudentRepository studentRepo, PasswordEncoder passwordEncoder) {
		super();
		this.studentRepo = studentRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public void storeStudent(StudentDTO studentDTO) {
		Student student =  new Student();
		student.setName(studentDTO.getName());
		student.setEmail(studentDTO.getEmail());
		student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
		student.setRole("ROLE-USER");
		studentRepo.save(student);
	}
}
