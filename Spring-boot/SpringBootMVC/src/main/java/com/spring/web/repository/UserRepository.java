package com.spring.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.web.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
