package com.coursemanagement.service;

import com.coursemanagement.dto.CertificateDTO;
import com.coursemanagement.model.*;
import com.coursemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CertificateService(CertificateRepository certificateRepository, 
                             UserRepository userRepository,
                             CourseRepository courseRepository, 
                             EnrollmentRepository enrollmentRepository) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Generate a certificate for a student who completed a course
     */
    public CertificateDTO generateCertificate(Long studentId, Long courseId, Double finalScore) {
        // Validate student exists
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        // Validate course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        // Check if enrollment exists and is completed
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found for student " + studentId + " and course " + courseId));

        if (!"COMPLETED".equals(enrollment.getStatus())) {
            throw new RuntimeException("Course must be completed before generating certificate");
        }

        // Check if certificate already exists
        if (certificateRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            throw new RuntimeException("Certificate already exists for this course and student");
        }

        // Create new certificate
        Certificate certificate = new Certificate();
        certificate.setStudent(student);
        certificate.setCourse(course);
        certificate.setFinalScore(finalScore);
        certificate.setCertificateNumber(generateCertificateNumber(studentId, courseId));
        certificate.setIssuedAt(LocalDateTime.now());
        
        // Set skills (you can customize this based on course)
        certificate.setSkills(course.getCategory() + ", " + course.getLevel());

        // Save certificate
        Certificate savedCertificate = certificateRepository.save(certificate);

        // Update enrollment with certificate info
        enrollment.setCertificateIssued(true);
        enrollment.setCertificateUrl("/api/student/certificates/download/" + savedCertificate.getId());
        enrollmentRepository.save(enrollment);

        return convertToDTO(savedCertificate);
    }

    /**
     * Get all certificates for a student
     */
    public List<CertificateDTO> getStudentCertificates(Long studentId) {
        // Validate student exists
        if (!userRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        return certificateRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get certificate by ID
     */
    public CertificateDTO getCertificateById(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));
        return convertToDTO(certificate);
    }

    /**
     * Get certificate by certificate number (for verification)
     */
    public CertificateDTO getCertificateByNumber(String certificateNumber) {
        Certificate certificate = certificateRepository.findByCertificateNumber(certificateNumber)
                .orElseThrow(() -> new RuntimeException("Certificate not found with number: " + certificateNumber));
        return convertToDTO(certificate);
    }

    /**
     * Verify if a certificate is valid
     */
    public boolean verifyCertificate(String certificateNumber) {
        return certificateRepository.findByCertificateNumber(certificateNumber).isPresent();
    }

    /**
     * Get all certificates (Admin only)
     */
    public List<CertificateDTO> getAllCertificates() {
        return certificateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Delete certificate
     */
    public void deleteCertificate(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));
        
        // Update enrollment
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(
                certificate.getStudent().getId(), 
                certificate.getCourse().getId()
        ).orElse(null);
        
        if (enrollment != null) {
            enrollment.setCertificateIssued(false);
            enrollment.setCertificateUrl(null);
            enrollmentRepository.save(enrollment);
        }
        
        certificateRepository.deleteById(id);
    }

    /**
     * Generate unique certificate number
     */
    private String generateCertificateNumber(Long studentId, Long courseId) {
        String prefix = "CERT";
        String year = String.valueOf(LocalDateTime.now().getYear());
        String uniqueId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("%s-%s-%d-%d-%s", prefix, year, studentId, courseId, uniqueId);
    }

    /**
     * Convert Certificate entity to DTO
     */
    private CertificateDTO convertToDTO(Certificate certificate) {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(certificate.getId());
        dto.setStudentId(certificate.getStudent().getId());
        dto.setStudentName(certificate.getStudent().getName());
        dto.setCourseId(certificate.getCourse().getId());
        dto.setCourseTitle(certificate.getCourse().getTitle());
        dto.setCertificateNumber(certificate.getCertificateNumber());
        dto.setIssuedAt(certificate.getIssuedAt());
        dto.setPdfUrl(certificate.getPdfUrl());
        dto.setFinalScore(certificate.getFinalScore());
        return dto;
    }

    /**
     * Generate PDF certificate (placeholder - implement with iText or similar)
     */
    public byte[] generatePdfCertificate(Long certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));
        
        // TODO: Implement PDF generation using iText or Apache PDFBox
        // For now, return placeholder
        String pdfContent = String.format(
            "Certificate of Completion\n\n" +
            "This certifies that %s\n" +
            "has successfully completed the course\n" +
            "%s\n\n" +
            "Score: %.2f%%\n" +
            "Certificate Number: %s\n" +
            "Date: %s",
            certificate.getStudent().getName(),
            certificate.getCourse().getTitle(),
            certificate.getFinalScore(),
            certificate.getCertificateNumber(),
            certificate.getIssuedAt().toString()
        );
        
        return pdfContent.getBytes();
    }
}