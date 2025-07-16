package com.employee.Management.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

private final UserRepository userRepository;

public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
}

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    user.getRoles().size(); // Eagerly load roles
    return new CustomUserDetails(user);
}
}
