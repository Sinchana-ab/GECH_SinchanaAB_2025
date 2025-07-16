package com.employee.Management.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableWebSecurity
@Configuration
public class WebSecurity {

private final CustomUserDetailsService userDetailsService;
private final CustomSuccessAuthenticationHandler successHandler;

public WebSecurity(CustomUserDetailsService userDetailsService,
                   CustomSuccessAuthenticationHandler successHandler) {
    this.userDetailsService = userDetailsService;
    this.successHandler = successHandler;
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return provider;
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(successHandler)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        .exceptionHandling(exception -> exception
            .accessDeniedPage("/403")
        )
        .build();
}
}

