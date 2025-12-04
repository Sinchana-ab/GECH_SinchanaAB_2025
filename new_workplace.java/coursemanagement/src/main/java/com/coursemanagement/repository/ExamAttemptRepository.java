//package com.coursemanagement.repository;
//
//
//import com.coursemanagement.model.ExamAttempt;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
//    
//    // Find all attempts by a student for a specific exam
//    List<ExamAttempt> findByStudentIdAndExamIdOrderByAttemptNumberDesc(Long studentId, Long examId);
//    
//    // Find attempts by enrollment (for certificate eligibility)
//    List<ExamAttempt> findByEnrollmentIdOrderByPercentageDesc(Long enrollmentId);
//    
//    // Find best attempt for a student in an exam
//    List<ExamAttempt> findByStudentIdAndExamIdOrderByPercentageDesc(Long studentId, Long examId);
//    
//    // Count attempts for limiting max attempts
//    Long countByStudentIdAndExamId(Long studentId, Long examId);
//    
//    // Find in-progress attempts
//    List<ExamAttempt> findByStudentIdAndStatus(Long studentId, String status);
//}
//

package com.coursemanagement.repository;

import com.coursemanagement.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
    
    // Find all attempts by a student for a specific exam
    List<ExamAttempt> findByStudentIdAndExamIdOrderByAttemptNumberDesc(Long studentId, Long examId);
    
    // Find attempts by enrollment (for certificate eligibility)
    List<ExamAttempt> findByEnrollmentIdOrderByPercentageDesc(Long enrollmentId);
    
    // Find best attempt for a student in an exam
    List<ExamAttempt> findByStudentIdAndExamIdOrderByPercentageDesc(Long studentId, Long examId);
    
    // Count attempts for limiting max attempts
    Long countByStudentIdAndExamId(Long studentId, Long examId);
    
    // Find in-progress attempts
    List<ExamAttempt> findByStudentIdAndStatus(Long studentId, String status);
    
    // Find by enrollment and status
    List<ExamAttempt> findByEnrollmentIdAndStatus(Long enrollmentId, String status);
    List<ExamAttempt> findByEnrollment_StudentId(Long studentId);
}

