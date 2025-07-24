package com.employee.Management.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        request.getSession().setAttribute("success", "Login successful!");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Check if the user has the ADMIN role
        boolean isAdmin = authorities.stream()
                                     .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            response.sendRedirect("/admin/dashboard"); // Redirect ADMIN to admin dashboard
        } else {
            response.sendRedirect("/employee/dashboard"); // Redirect other users to employee dashboard
        }
    }
}
