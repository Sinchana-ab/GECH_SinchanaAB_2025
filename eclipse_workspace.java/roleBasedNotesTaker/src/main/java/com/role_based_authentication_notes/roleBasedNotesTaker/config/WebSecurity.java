package com.role_based_authentication_notes.roleBasedNotesTaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.role_based_authentication_notes.roleBasedNotesTaker.services.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurity {
	
	private CustomUserDetailsService userDetailsService;
	private CustomSuccessAuthenticationHandler handler;
	
	

	public WebSecurity(CustomUserDetailsService userDetailsService, CustomSuccessAuthenticationHandler handler) {
		super();
		this.userDetailsService = userDetailsService;
		this.handler = handler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> auth
					.requestMatchers("/users/**","/roles/**").hasRole("ADMIN")
					.requestMatchers("/login","/register").permitAll()
					.anyRequest().authenticated()
				)
				.formLogin(login -> login
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.successHandler(handler)
					.permitAll()
				)
				.exceptionHandling(exception -> exception.accessDeniedPage("/403"))
				.logout(logout -> logout
					.permitAll()
				)
				.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}
