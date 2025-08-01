package com.employee.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.Management.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}