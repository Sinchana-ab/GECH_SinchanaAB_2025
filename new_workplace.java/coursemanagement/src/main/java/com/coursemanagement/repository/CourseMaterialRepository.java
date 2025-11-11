package com.coursemanagement.repository;

import com.coursemanagement.model.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
    List<CourseMaterial> findByCourseIdOrderByOrderIndexAsc(Long courseId);
    void deleteByCourseId(Long courseId);
}
