package com.student.UserCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.UserCrud.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
