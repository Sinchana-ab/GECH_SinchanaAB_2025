package com.db.relationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.relationships.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
