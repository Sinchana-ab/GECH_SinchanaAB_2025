//package com.coursemanagement.repository;
//
//import com.coursemanagement.model.CourseMaterial;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
//    List<CourseMaterial> findByCourseIdOrderByOrderIndexAsc(Long courseId);
//    void deleteByCourseId(Long courseId);
//    boolean existsByEnrollmentId(Long enrollmentId);
//}
//
package com.coursemanagement.repository;

import com.coursemanagement.model.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
    
    // Find materials by course ID, ordered by orderIndex
    List<CourseMaterial> findByCourseIdOrderByOrderIndexAsc(Long courseId);
    
    // Count materials in a course
    Long countByCourseId(Long courseId);
    
    // Find materials by type
    List<CourseMaterial> findByMaterialType(String materialType);

    void deleteByCourseId(Long courseId);
    // REMOVED: existsByEnrollmentId - This was wrong!
    // CourseMaterial doesn't have enrollmentId field
}