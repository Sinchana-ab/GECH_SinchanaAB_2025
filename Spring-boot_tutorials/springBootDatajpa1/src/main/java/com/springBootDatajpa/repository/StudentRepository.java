package com.springBootDatajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springBootDatajpa.model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>  {
	List<Student> findByEmail(String email);
}
