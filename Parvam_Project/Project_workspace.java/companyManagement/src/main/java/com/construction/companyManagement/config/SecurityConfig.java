package com.construction.companyManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity; consider enabling it in production
            .csrf(csrf -> csrf.disable())
         
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").authenticated()  // Secure /admin/** routes
                .anyRequest().permitAll()  // Allow all other routes without authentication
            )
            
            .formLogin(form -> form
                .loginPage("/admin/login")  // Custom login page URL
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin/dashboard")
                .permitAll()  // Allow access to the login page for all users
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")  // Logout URL
                .permitAll()  // Allow everyone to access the logout functionality
            );
        
        return http.build();
    }
}