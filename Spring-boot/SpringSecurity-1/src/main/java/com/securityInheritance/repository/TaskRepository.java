package com.securityInheritance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securityInheritance.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
