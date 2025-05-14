package com.springSecurityDemo.springSecurityDemo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurityDemo.springSecurityDemo.dto.StudentDTO;
import com.springSecurityDemo.springSecurityDemo.model.Student;
import com.springSecurityDemo.springSecurityDemo.repository.StudentRepo;

import jakarta.mail.MessagingException;

@Service
public class StudentService {

	private StudentRepo studentRepo;
	private PasswordEncoder passwordEncoder;
	private EmailService emailService;

	

	public StudentService(StudentRepo studentRepo, PasswordEncoder passwordEncoder, EmailService emailService) {
		super();
		this.studentRepo = studentRepo;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}



	public void storeStudent(StudentDTO studentDTO) throws MessagingException {
		Student student = new Student();

		student.setName(studentDTO.getName());
		student.setEmail(studentDTO.getEmail());
		student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
		student.setRole("ROLE_USER");
		studentRepo.save(student);
		
		emailService.sendEmail(studentDTO.getEmail(),studentDTO.getEmail(),studentDTO.getPassword(), studentDTO.getName());
	}
}
