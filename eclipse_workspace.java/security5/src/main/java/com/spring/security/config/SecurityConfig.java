package com.spring.security.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import static org.springframework.security.config.Customizer.withDefaults;

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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth-> auth
						.requestMatchers("/","/about","/contact","/register").permitAll()
						.anyRequest().authenticated()
						)
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/std-details",true)
						.permitAll()
						)
				.logout(logout->logout
						.logoutSuccessUrl("/login?logout")
						.permitAll())
				.build();
				
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
