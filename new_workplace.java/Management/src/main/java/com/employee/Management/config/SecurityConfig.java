package com.employee.Management.config;

import com.employee.Management.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationSuccessHandler customSuccessAuthenticationHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          AuthenticationSuccessHandler customSuccessAuthenticationHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customSuccessAuthenticationHandler = customSuccessAuthenticationHandler;
    }

    // Defines the password encoder to be used for hashing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configures the authentication provider, telling Spring Security how to find users and verify passwords
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Uses our custom user details service
        authProvider.setPasswordEncoder(passwordEncoder()); // Uses our defined password encoder
        return authProvider;
    }

    // Defines the security filter chain, which specifies authorization rules, login, and logout behavior
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in development; enable in production
            .authorizeHttpRequests(authorize -> authorize
                // PUBLICLY ACCESSIBLE PAGES (MUST BE AT THE TOP)
                .requestMatchers(
                    "/", "/index", "/home", "/login", "/register",
                    "/employees", "/departments", "/roles", // These are view pages, accessible to all
                    "/css/**", "/js/**", "/images/**" // Static resources
                ).permitAll()
                // ADMIN-SPECIFIC PAGES (require ADMIN role)
                .requestMatchers(
                    "/admin/**"
                ).hasRole("ADMIN")
                // EMPLOYEE DASHBOARD AND ITS SUBPAGES (require any authenticated user)
                .requestMatchers(
                    "/employee/dashboard",
                    "/my-profile",
                    "/leave-requests",
                    "/leave-requests/**",
                    "/documents"
                ).authenticated()
                // ALL OTHER REQUESTS (catch-all, require authentication)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Specifies the custom login page URL
                .loginProcessingUrl("/login") // The URL to which the login form data is submitted
                .successHandler(customSuccessAuthenticationHandler) // Custom handler for successful login
                .failureUrl("/login?error=true") // URL to redirect to on failed login
                .permitAll() // Allow everyone to access the login page
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL to trigger logout
                .logoutSuccessUrl("/login?logout=true") // URL to redirect to after successful logout
                .invalidateHttpSession(true) // Invalidate HTTP session
                .deleteCookies("JSESSIONID") // Delete session cookies
                .permitAll() // Allow everyone to access the logout functionality
            );

        return http.build();
    }
}
