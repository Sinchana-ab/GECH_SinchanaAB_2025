package com.Form.Validation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Form.Validation.model.StudentValidations;

public interface StudentRepository extends JpaRepository<StudentValidations, Long> {
	/*
	 * findBy-> return object
	 * existBy-> boolean*/
	public StudentValidations findByEmail(String email);
}
