package com.springSecurity.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurity.dto.UsersDTO;
import com.springSecurity.model.Users;
import com.springSecurity.repository.UsersRepository;

@Service
public class UsersServices {

    private final UsersRepository repository;
    private final PasswordEncoder encoder;

    public UsersServices(UsersRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void saveStudent(UsersDTO dto) {
        Users users = new Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        users.setPassword(encoder.encode(dto.getPassword()));
        users.setRole("ROLE_USER"); // make sure role is saved with ROLE_ prefix
        repository.save(users);
    }

	public List<Users> getAllUsers() {
		List<Users> users = repository.findAll();
		return users;
	}
}
