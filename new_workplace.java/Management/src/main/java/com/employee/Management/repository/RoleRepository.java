package com.employee.Management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.Management.model.Role;
import com.employee.Management.model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}