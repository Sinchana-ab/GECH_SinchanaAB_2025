package com.employee.Management.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessAuthenticationHandler implements AuthenticationSuccessHandler {
public void onAuthenticationSuccess(HttpServletRequest request,
HttpServletResponse response,
Authentication authentication) throws IOException, ServletException {
request.getSession().setAttribute("success", "Login successful!");
response.sendRedirect("/");
}
}
