package com.role_based_authentication_notes.roleBasedNotesTaker.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional 
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;
		
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("2222222222222"+username);
		User user = userRepository.findByEmail(username).orElseThrow(
				()->new UsernameNotFoundException("User not found with provided email "+username));
		System.out.println(user+"11111111111111111111");
		// Force initialize roles collection while still in transaction/session
	    user.getRoles().size();
		return new CustomUserDetails(user);
	}

}
