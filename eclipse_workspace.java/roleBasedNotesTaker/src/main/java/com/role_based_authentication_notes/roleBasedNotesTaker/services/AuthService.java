package com.role_based_authentication_notes.roleBasedNotesTaker.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.role_based_authentication_notes.roleBasedNotesTaker.dto.UserDTO;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Address;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Role;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.User;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.RoleRepository;
import com.role_based_authentication_notes.roleBasedNotesTaker.repository.UserRepository;

@Service
public class AuthService {
	
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,RoleRepository roleRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}



	public void saveUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		Address address = new Address();
		address.setAddress(userDTO.getAddress());
		user.setAddress(address);
		if(userDTO.getRoles().isEmpty()) {
			Role defaultRole = roleRepository.findByName("USER")
			        .orElseThrow(() -> new RuntimeException("Default role not found"));
			user.getRoles().add(defaultRole);
		}else {
			List<Role> roles =  roleRepository.findAllById(userDTO.getRoles());
			user.setRoles( new HashSet<>(roles));
		}
		userRepository.save(user);
	}



	public void updateUser(UserDTO userDTO, Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("user not found"));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		if(! userDTO.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		Address address = new Address();
		address.setAddress(userDTO.getAddress());
		user.setAddress(address);
		List<Role> roles =  roleRepository.findAllById(userDTO.getRoles());
		for(var role :user.getRoles()) {
			role.getUsers().remove(user);
		}
		user.setRoles( new HashSet<>(roles));
		userRepository.save(user);
	}
}
