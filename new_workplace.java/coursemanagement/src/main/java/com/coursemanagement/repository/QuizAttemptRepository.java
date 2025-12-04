package com.coursemanagement.repository;

import com.coursemanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByStudentIdAndQuizId(Long studentId, Long quizId);
    List<QuizAttempt> findByQuizId(Long quizId);
   
    @Query("SELECT COUNT(qa) FROM QuizAttempt qa WHERE qa.student.id = :studentId AND qa.quiz.id = :quizId")
    Long countByStudentIdAndQuizId(@Param("studentId") Long studentId, @Param("quizId") Long quizId);
    
    @Query("SELECT qa FROM QuizAttempt qa WHERE qa.student.id = :studentId AND qa.quiz.course.id = :courseId")
    List<QuizAttempt> findByStudentIdAndQuizCourseId(
        @Param("studentId") Long studentId, 
        @Param("courseId") Long courseId
    );
    
    // ✅ ADDED: Find attempts by student ID and status
    List<QuizAttempt> findByStudentIdAndStatus(Long studentId, String status);
}