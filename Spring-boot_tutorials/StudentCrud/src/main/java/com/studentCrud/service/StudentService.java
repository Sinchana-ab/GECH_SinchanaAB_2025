package com.studentCrud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studentCrud.dto.StudentDTO;
import com.studentCrud.model.Student;
import com.studentCrud.repository.StudentRepository;

@Service
public class StudentService {
	
	private StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}
	
	public List<Student> getAllStudents(){
		List<Student> students = studentRepository.findAll();
		return students;
	}

	public void saveStudent(StudentDTO studentDTO) {
		// TODO Auto-generated method stub
		Student student = new  Student();
		student.setName(studentDTO.getName());
		student.setEmail(studentDTO.getEmail());
		student.setNumber(studentDTO.getNumber());
		studentRepository.save(student);
	}
}
