package com.coursemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coursemanagement.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
List<Exam> findByCourseIdAndPublishedTrue(Long courseId);
    
    Optional<Exam> findByCourseIdAndIsFinalExamTrue(Long courseId);
    
    List<Exam> findByCourseIdOrderByCreatedAtDesc(Long courseId);

}
