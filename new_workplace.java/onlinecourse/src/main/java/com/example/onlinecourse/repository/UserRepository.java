package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.User;
import com.example.onlinecourse.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // add this method so controller can query instructors
    List<User> findByRole(Role role);
}
