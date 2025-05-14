package com.springSecurityDemo.springSecurityDemo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springSecurityDemo.springSecurityDemo.model.Student;
import com.springSecurityDemo.springSecurityDemo.repository.StudentRepo;

public class CustomUserDetailsService implements UserDetailsService {

	private StudentRepo studentRepo;
	
	public CustomUserDetailsService(StudentRepo studentRepo) {
		super();
		this.studentRepo = studentRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentRepo.findByEmail(username)
				.orElseThrow(
						() ->new UsernameNotFoundException("username not found: "+username)
				);
		return new CustomUserDetails(student);
	}

}
