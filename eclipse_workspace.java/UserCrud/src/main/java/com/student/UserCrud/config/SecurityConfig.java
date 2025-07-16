package com.student.UserCrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		return http 
				.authorizeHttpRequests(auth ->
					auth.requestMatchers("/","about","/register").permitAll()
					.anyRequest().authenticated())
				.formLogin(login -> login
						.loginProcessingUrl("/srudentList")
						.defaultSuccessUrl("/studentList").permitAll())
				.logout(logout  -> 
						logout.logoutSuccessUrl("/login?logout").permitAll()
						)
				.build();
	}
}
