package com.student.UserCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.UserCrud.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
