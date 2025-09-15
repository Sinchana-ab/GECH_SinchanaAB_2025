package com.studentCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentCrud.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
