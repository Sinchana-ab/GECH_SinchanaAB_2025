package com.securityInheritance.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.securityInheritance.repository.UserRepository;
@Service
public class CustomerUserDetailsService implements UserDetailsService {

	public CustomerUserDetailsService(UserRepository repository) {
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return null;
	}

}
