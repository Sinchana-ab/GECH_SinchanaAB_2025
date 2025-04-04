package com.Form.Validation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Form.Validation.dto.StudentDTO;
import com.Form.Validation.model.StudentValidations;
import com.Form.Validation.repository.StudentRepository;
@Service
public class StudentService {
	
	private StudentRepository studentRepository;
	
	public StudentService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}
	
	

	public void saveStudent(StudentDTO studentDTO) {
		StudentValidations student = new StudentValidations();
		student.setName(studentDTO.getName());
		student.setAge(studentDTO.getAge());
		student.setEmail(studentDTO.getEmail());
		student.setPassword(studentDTO.getPassword());
		studentRepository.save(student);
	}



	public List<StudentValidations> getAllStudents() {
		List<StudentValidations> students =  studentRepository.findAll();
		return students;
	}



	public void deleteStudent(Long id) {
		StudentValidations student = studentRepository.findById(id).get();
		studentRepository.delete(student);
		
	}



	public StudentDTO editStudent(Long id) {
		StudentValidations student = studentRepository.findById(id).get();
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setName(student.getName());
		studentDTO.setAge(student.getAge());
		studentDTO.setEmail(student.getEmail());
		studentDTO.setPassword(student.getPassword());
		return studentDTO;
	}

	public void updateStudent(StudentDTO studentDTO,Long id) {
		StudentValidations student = studentRepository.findById(id).get();
		student.setName(studentDTO.getName());
		student.setAge(studentDTO.getAge());
		student.setEmail(studentDTO.getEmail());
		student.setPassword(studentDTO.getPassword());
		studentRepository.save(student);
	}
}
