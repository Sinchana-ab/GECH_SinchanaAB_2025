package com.construction.companyManagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.construction.companyManagement.service.CustomAdminDetailsService;
import com.construction.companyManagement.service.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler; // Autowire the new handler

    @Autowired
    private CustomAdminDetailsService adminDetailsService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // A single, unified authentication provider that can handle both
    @Bean
    UserDetailsService userDetailsService() {
        return new CompositeUserDetailsService(adminDetailsService, userDetailsService);
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider())
            .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                        "/", "/about", "/contact", "/projects", "/blog", "/gallery", "/testimonials", "/team",
                        "/images/**", "/uploads/**", "/css/**", "/js/**",
                        "/register", "/login" 
                    ).permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/user/**").hasAnyRole("USER")
                    .anyRequest().authenticated()
                )
                .formLogin(login -> login
                    .loginPage("/login") // Use the new, unified login page
                    .loginProcessingUrl("/login") // Submit the form to this URL
                    .successHandler(customAuthSuccessHandler) // Use the custom handler for redirection
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout") // Redirect to the login page with a logout message
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                )
                .build();
    }
}