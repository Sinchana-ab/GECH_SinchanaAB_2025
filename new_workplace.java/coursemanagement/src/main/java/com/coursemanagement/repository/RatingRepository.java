package com.coursemanagement.repository;

import com.coursemanagement.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCourseId(Long courseId);
    List<Rating> findByStudentId(Long studentId);
    Optional<Rating> findByStudentIdAndCourseId(Long studentId, Long courseId);
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.course.id = :courseId")
    Double getAverageRatingByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.course.id = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT r FROM Rating r WHERE r.course.id = :courseId ORDER BY r.helpfulCount DESC")
    List<Rating> findTopRatedByCourseId(@Param("courseId") Long courseId);
}
