//package com.coursemanagement.repository;
//
//import com.coursemanagement.model.*;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface CertificateRepository extends JpaRepository<Certificate, Long> {
//	 List<Certificate> findByStudentId(Long studentId);
//
//	    Optional<Certificate> findByStudentIdAndCourseId(Long studentId, Long courseId);
//
//	    Optional<Certificate> findByCertificateNumber(String certificateNumber);
//
//	    // ✔ REQUIRED METHOD FOR YOUR SERVICE
//	    boolean existsByEnrollmentId(Long enrollmentId);
//}
package com.coursemanagement.repository;

import com.coursemanagement.model.Certificate;
import com.coursemanagement.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
    // Check if certificate exists for enrollment
    boolean existsByEnrollmentId(Long enrollmentId);
    
    // Find certificate by enrollment
    Optional<Certificate> findByEnrollmentId(Long enrollmentId);
    
    // Find all certificates for a student
    List<Certificate> findByStudentIdOrderByIssuedAtDesc(Long studentId);
    
    // Find certificate by certificate number
    Optional<Certificate> findByCertificateNumber(String certificateNumber);
    
    // Find certificates by course
    List<Certificate> findByCourseId(Long courseId);

	Optional<Certificate> findByStudentIdAndCourseId(Long studentId, Long courseId);

	List<Certificate> findByStudentId(Long studentId);
}