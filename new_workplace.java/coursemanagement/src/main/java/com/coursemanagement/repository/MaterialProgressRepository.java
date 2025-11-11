package com.coursemanagement.repository;

import com.coursemanagement.model.MaterialProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialProgressRepository extends JpaRepository<MaterialProgress, Long> {
    
    Optional<MaterialProgress> findByStudentIdAndMaterialIdAndEnrollmentId(
        Long studentId, Long materialId, Long enrollmentId);
    
    List<MaterialProgress> findByEnrollmentId(Long enrollmentId);
    
    List<MaterialProgress> findByStudentIdAndEnrollmentId(Long studentId, Long enrollmentId);
    
    @Query("SELECT COUNT(mp) FROM MaterialProgress mp WHERE mp.enrollment.id = :enrollmentId AND mp.completed = true")
    Long countCompletedMaterialsByEnrollment(Long enrollmentId);
    
    @Query("SELECT COUNT(mp) FROM MaterialProgress mp WHERE mp.enrollment.id = :enrollmentId")
    Long countTotalMaterialsByEnrollment(Long enrollmentId);
}