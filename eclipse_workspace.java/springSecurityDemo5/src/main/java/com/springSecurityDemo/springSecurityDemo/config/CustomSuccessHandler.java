package com.springSecurityDemo.springSecurityDemo.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//Collection<? extends GrantedAuthority>	authorities =  authentication.getAuthorities();
		var	authorities =  authentication.getAuthorities();
		if(authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN"))) {
			response.sendRedirect("/admin");
		}else if(authorities.stream().anyMatch(a->a.getAuthority().equals("ROLE_USER"))) {
			response.sendRedirect("/user");
		}else {
			response.sendRedirect("/std-details");
		}

	}
	
}