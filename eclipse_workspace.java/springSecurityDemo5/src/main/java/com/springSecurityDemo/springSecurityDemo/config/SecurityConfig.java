package com.springSecurityDemo.springSecurityDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springSecurityDemo.springSecurityDemo.repository.StudentRepo;
import com.springSecurityDemo.springSecurityDemo.service.CustomUserDetailsService;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
	
	private StudentRepo studentRepo;
	private CustomSuccessHandler customSuccessHandler;
	

	public SecurityConfig(StudentRepo studentRepo, CustomSuccessHandler customSuccessHandler) {
		super();
		this.studentRepo = studentRepo;
		this.customSuccessHandler = customSuccessHandler;
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService(studentRepo);
	}
	
	@Bean
	public DaoAuthenticationProvider doAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth-> auth
						//.requestMatchers("/user").hasRole("USER")
						.requestMatchers("/user").hasAnyRole("USER","ADMIN")
						.requestMatchers("/admin").hasRole("ADMIN")
						.requestMatchers("/","/about","/contact","/register").permitAll()
						.anyRequest().authenticated()
						)
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						//.defaultSuccessUrl("/std-details",true)
						.successHandler(customSuccessHandler)
						.permitAll()
						)
				.logout(logout->logout
						.logoutSuccessUrl("/login?logout")
						.permitAll())
				.exceptionHandling(
						exp -> exp.accessDeniedPage("/error")
						)
				.build();
	}
}
