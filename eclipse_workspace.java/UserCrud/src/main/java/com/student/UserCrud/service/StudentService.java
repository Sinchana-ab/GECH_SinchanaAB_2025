package com.student.UserCrud.service;

import com.student.UserCrud.dto.StudentDTO;
import com.student.UserCrud.dto.UserDTO;
import com.student.UserCrud.model.Student;
import com.student.UserCrud.repository.StudentRepository;

public class StudentService {
	
	private StudentRepository studentRepository;
	
	

	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}



	public void saveStudent(StudentDTO studentDTO) {
		Student student = new Student();
		student.setName(studentDTO.getName());
		student.setAge(studentDTO.getAge());
		student.setEmail(studentDTO.getEmail());
	}
	
}
