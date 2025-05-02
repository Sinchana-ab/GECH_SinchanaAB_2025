package com.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.security.repository.StudentRepository;
import com.spring.security.service.CustomerDetailesService;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
	
	private StudentRepository studentRepository;
	
	public SecurityConfig(StudentRepository studentRepository, UserDetailsService userDetailsService) {
		super();
		this.studentRepository = studentRepository;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	public UserDetailsService userDetailsService() {
		return new CustomerDetailesService(studentRepository);
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
}
