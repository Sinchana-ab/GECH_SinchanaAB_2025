package com.userRelation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userRelation.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
