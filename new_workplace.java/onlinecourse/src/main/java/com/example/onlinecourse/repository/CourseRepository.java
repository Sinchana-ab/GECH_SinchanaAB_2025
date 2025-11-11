package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByPublishedTrue();
    List<Course> findByInstructorId(Long instructorId);
}
