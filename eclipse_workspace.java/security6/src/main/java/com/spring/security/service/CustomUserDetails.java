package com.spring.security.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.security.models.Student;

public class CustomUserDetails implements UserDetails {
	
	private Student student;
	
	
	public CustomUserDetails(Student student) {
		super();
		this.student = student;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(student.getRole()));
	}

	@Override
	public String getPassword() {
		
		return student.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return student.getEmail();
	}

}
