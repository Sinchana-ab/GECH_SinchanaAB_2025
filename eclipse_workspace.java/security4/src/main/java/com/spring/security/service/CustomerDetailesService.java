package com.spring.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.security.models.Student;
import com.spring.security.repository.StudentRepository;
@Service
public class CustomerDetailesService  implements UserDetailsService{
	
	private StudentRepository studentRepository;
	
	
	public CustomerDetailesService(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepository.findByEmail(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("username not found: "+username)
						);
		
		return new CustomUserDetails(student);
	}

}
