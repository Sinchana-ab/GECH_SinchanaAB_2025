package com.Form.Validation.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
		MultipartFile image = studentDTO.getImage();
		MultipartFile resume = studentDTO.getResume();
		Date createdAt = new Date();
		String storeImageName = createdAt.getTime()+"_"+image.getOriginalFilename();
		String storeResumeName = createdAt.getTime()+"_"+resume.getOriginalFilename();
		System.out.println(storeImageName);
		System.out.println(storeResumeName);
		
		try {
			String uploadDir = "public/image/";
			String uploadDirr = "public/resume/";
			Path uploadPath = Paths.get(uploadDir);
			Path uploadPath1 = Paths.get(uploadDirr);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			if(!Files.exists(uploadPath1)) {
				Files.createDirectories(uploadPath1);
			}
			try {
				InputStream inputStream = image.getInputStream();
				InputStream inputStream1 = resume.getInputStream();
				Files.copy(inputStream1,Paths.get(uploadDirr+storeResumeName),StandardCopyOption.REPLACE_EXISTING);
				Files.copy(inputStream,Paths.get(uploadDir+storeImageName),StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		StudentValidations student = new StudentValidations();
		student.setName(studentDTO.getName());
		student.setAge(studentDTO.getAge());
		student.setEmail(studentDTO.getEmail());
		student.setPassword(studentDTO.getPassword());
		student.setImagePath(storeImageName);
		student.setResumePath(storeResumeName);
		studentRepository.save(student);
	}



	public List<StudentValidations> getAllStudents() {
		List<StudentValidations> students =  studentRepository.findAll();
		return students;
	}



	public void deleteStudent(Long id) {
		StudentValidations student = studentRepository.findById(id).get();
		String uploadDir = "public/image/";
		String uploadDir1 = "public/resume/";
		Path imagePath = Paths.get(uploadDir+student.getImagePath());
		Path resumePath = Paths.get(uploadDir1+student.getResumePath());
		try {
			Files.delete(imagePath);
			Files.delete(resumePath);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
		if(!studentDTO.getImage().isEmpty()) {
			Path oldImagePath = Paths.get("public/image/"+student.getImagePath());
			try {
				Files.delete(oldImagePath);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			MultipartFile image = studentDTO.getImage();
			Date createdAt = new Date();
			String storeImageName = createdAt.getTime()+"_"+image.getOriginalFilename();
			String uploadDir = "public/image/";
			try {
				InputStream inputStream = image.getInputStream();
				Files.copy(inputStream, Paths.get(uploadDir+storeImageName), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			student.setImagePath(storeImageName);
		}
		
		if(!studentDTO.getResume().isEmpty()) {
			Path oldResumePath = Paths.get("public/resume/"+student.getResumePath());
			try {
				Files.delete(oldResumePath);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			MultipartFile resume = studentDTO.getResume();
			Date createdAt = new Date();
			String storeResumeName = createdAt.getTime()+"_"+resume.getOriginalFilename();
			String uploadDir1 = "public/resume/";
			try {
				InputStream inputStream = resume.getInputStream();
				Files.copy(inputStream, Paths.get(uploadDir1+storeResumeName), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			student.setResumePath(storeResumeName);
		}
		
		student.setName(studentDTO.getName());
		student.setAge(studentDTO.getAge());
		student.setEmail(studentDTO.getEmail());
		student.setPassword(studentDTO.getPassword());
		
		studentRepository.save(student);
	}
}
