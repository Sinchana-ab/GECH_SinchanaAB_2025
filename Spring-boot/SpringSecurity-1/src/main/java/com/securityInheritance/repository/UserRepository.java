package com.securityInheritance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securityInheritance.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
