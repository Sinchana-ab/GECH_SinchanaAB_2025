package com.springBootDatajpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springBootDatajpa.model.Student;
import com.springBootDatajpa.repository.StudentRepository;

@Component
public class SpringData {

    private final SpringBootDatajpa1Application springBootDatajpa1Application;
	
	@Autowired
	StudentRepository repository;
	
	
	public SpringData(StudentRepository repository, SpringBootDatajpa1Application springBootDatajpa1Application) {
		super();
		this.repository = repository;
		this.springBootDatajpa1Application = springBootDatajpa1Application;
	}


	public void addStudent(){
		Student s = new Student("sinchana", "sinchana@gmail.com", 20);
		repository.save(s);
	}
	public void getStudent() {
		Student student = repository.findById(1).get();
		System.out.println("Student 1 details: "+student);
	}
	
	public void editStudent(int id) {
		Optional<Student> optional = repository.findById(id);
		if(optional.isPresent()) {
			Student student = optional.get();
			student.setName("nayana");
			student.setEmail("nayana@gmail.com");
			student.setAge(24);
			repository.save(student);
		}
		else {
			System.out.println("no student found");
		}
		
	}
	
	public void getStudentByEmail() {
		List<Student> semail = repository.findByEmail("sinchana@gmail.com");
		System.out.println("the email student details are: "+semail);
	}
}
