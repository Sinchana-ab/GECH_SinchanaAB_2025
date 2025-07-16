package com.student.UserCrud.service;

import org.springframework.stereotype.Service;

import com.student.UserCrud.dto.UserDTO;
import com.student.UserCrud.model.User;
import com.student.UserCrud.repository.UserRepository;
@Service
public class UserService {
	
	private  UserRepository userRepository;
	

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	public void saveUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setEmail(user.getEmail());
		user.setPassword(userDTO.getPassword());
		userRepository.save(user);
		
	}

}
