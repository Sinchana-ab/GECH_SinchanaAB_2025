package com.user.form.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.form.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
