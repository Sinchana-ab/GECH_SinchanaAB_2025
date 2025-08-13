package com.construction.companyManagement.config;

import com.construction.companyManagement.service.CustomAdminDetailsService;
import com.construction.companyManagement.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompositeUserDetailsService implements UserDetailsService {

    private final CustomAdminDetailsService adminDetailsService;
    private final CustomUserDetailsService userDetailsService;

    public CompositeUserDetailsService(CustomAdminDetailsService adminDetailsService, CustomUserDetailsService userDetailsService) {
        this.adminDetailsService = adminDetailsService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // First, try to load the user as an admin (using email as username)
            return adminDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            // If not found, try to load the user as a regular user
            try {
                return userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException ex) {
                // If not found in either, throw the original exception
                throw new UsernameNotFoundException("User not found with username or email: " + username);
            }
        }
    }
}