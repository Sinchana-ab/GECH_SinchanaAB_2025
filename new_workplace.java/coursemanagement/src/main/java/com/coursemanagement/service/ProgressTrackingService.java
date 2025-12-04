package com.coursemanagement.service;

import com.coursemanagement.dto.MaterialProgressDTO;
import com.coursemanagement.model.*;
import com.coursemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProgressTrackingService {

    private final MaterialProgressRepository progressRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseMaterialRepository materialRepository;
    private final CertificateRepository certificateRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final UserRepository userRepository;

    public ProgressTrackingService(
            MaterialProgressRepository progressRepository,
            EnrollmentRepository enrollmentRepository,
            CourseMaterialRepository materialRepository,
            CertificateRepository certificateRepository,
            ExamAttemptRepository examAttemptRepository,
            UserRepository userRepository) {

        this.progressRepository = progressRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.materialRepository = materialRepository;
        this.certificateRepository = certificateRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.userRepository = userRepository;
    }

    // ────────────────────────────────────────────────────────────────
    // MARK MATERIAL AS COMPLETE
    // ────────────────────────────────────────────────────────────────
    @Transactional
    public MaterialProgressDTO markMaterialAsComplete(Long studentId, Long materialId, Long enrollmentId) {

        Enrollment enrollment = validateEnrollment(studentId, enrollmentId);

        // Fetch student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Fetch existing progress or create new
        MaterialProgress progress = progressRepository
                .findByStudentIdAndMaterialId(studentId, materialId)
                .orElse(new MaterialProgress());

        if (progress.getId() == null) {
            CourseMaterial material = materialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Material not found"));

            progress.setStudent(student);
            progress.setEnrollment(enrollment);
            progress.setMaterial(material);
        }

        progress.setCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());
        progress.setLastAccessedAt(LocalDateTime.now());
        progress.setCompletionPercentage(100.0);

        if ("VIDEO".equalsIgnoreCase(progress.getMaterial().getMaterialType())) {
            progress.setWatchTimeSeconds(progress.getTotalDurationSeconds());
        }

        MaterialProgress saved = progressRepository.save(progress);

        updateEnrollmentProgress(enrollmentId);

        return convertToDTO(saved);
    }

    // ────────────────────────────────────────────────────────────────
    // UPDATE VIDEO WATCH PROGRESS
    // ────────────────────────────────────────────────────────────────
    @Transactional
    public MaterialProgressDTO updateVideoProgress(
            Long studentId, Long materialId, Long enrollmentId,
            Integer watchTimeSeconds, Integer totalDurationSeconds) {

        Enrollment enrollment = validateEnrollment(studentId, enrollmentId);

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        MaterialProgress progress = progressRepository
                .findByStudentIdAndMaterialId(studentId, materialId)
                .orElse(new MaterialProgress());

        if (progress.getId() == null) {
            CourseMaterial material = materialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Material not found"));

            progress.setStudent(student);
            progress.setEnrollment(enrollment);
            progress.setMaterial(material);
        }

        progress.setWatchTimeSeconds(watchTimeSeconds);
        progress.setTotalDurationSeconds(totalDurationSeconds);
        progress.setLastAccessedAt(LocalDateTime.now());

        if (totalDurationSeconds > 0) {
            double percentage = (double) watchTimeSeconds / totalDurationSeconds * 100;
            progress.setCompletionPercentage(percentage);

            if (percentage >= 90 && !progress.isCompleted()) {
                progress.setCompleted(true);
                progress.setCompletedAt(LocalDateTime.now());
            }
        }

        MaterialProgress saved = progressRepository.save(progress);

        updateEnrollmentProgress(enrollmentId);

        return convertToDTO(saved);
    }

    // ────────────────────────────────────────────────────────────────
    // GET STUDENT MATERIAL PROGRESS LIST
    // ────────────────────────────────────────────────────────────────
    public List<MaterialProgressDTO> getStudentMaterialProgress(Long studentId, Long enrollmentId) {

        validateEnrollment(studentId, enrollmentId);

        List<MaterialProgress> progressList =
                progressRepository.findByStudentIdOrderByMaterial_OrderIndexAsc(studentId);

        return progressList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ────────────────────────────────────────────────────────────────
    // UPDATE ENROLLMENT PROGRESS
    // ────────────────────────────────────────────────────────────────
    @Transactional
    public void updateEnrollmentProgress(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        List<CourseMaterial> allMaterials = materialRepository
                .findByCourseIdOrderByOrderIndexAsc(enrollment.getCourse().getId());

        if (allMaterials.isEmpty()) {
            enrollment.setProgress(0.0);
            enrollmentRepository.save(enrollment);
            return;
        }

        List<MaterialProgress> completed = progressRepository
                .findByStudentIdAndCompletedTrue(enrollment.getStudent().getId());

        double percentage = (double) completed.size() / allMaterials.size() * 100;

        enrollment.setProgress(percentage);

        if (percentage >= 100) {
            enrollment.setStatus("COMPLETED");
            enrollment.setCompletedAt(LocalDateTime.now());
        } else if (percentage > 0) {
            enrollment.setStatus("IN_PROGRESS");
        }

        enrollmentRepository.save(enrollment);
    }

    // ────────────────────────────────────────────────────────────────
    // CHECK CERTIFICATE ELIGIBILITY
    // ────────────────────────────────────────────────────────────────
    public Map<String, Object> checkCertificateEligibility(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Map<String, Object> result = new HashMap<>();

        boolean certificateExists = certificateRepository.existsByEnrollmentId(enrollmentId);

        if (certificateExists) {
            result.put("eligible", false);
            result.put("reason", "Certificate already issued");
            result.put("certificateExists", true);
            return result;
        }

        double progress = enrollment.getProgress() != null ? enrollment.getProgress() : 0;
        boolean completed = progress >= 100;

        List<ExamAttempt> attempts =
                examAttemptRepository.findByEnrollmentIdOrderByPercentageDesc(enrollmentId);

        boolean examPassed = false;
        Double score = null;

        if (!attempts.isEmpty()) {
            score = attempts.get(0).getPercentage();
            examPassed = score >= 50.0;
        }

        boolean eligible = completed && examPassed;

        result.put("eligible", eligible);
        result.put("progress", progress);
        result.put("materialsCompleted", completed);
        result.put("examPassed", examPassed);
        result.put("examScore", score);
        result.put("certificateExists", false);

        if (!completed)
            result.put("reason", "Course not fully completed");
        else if (!examPassed)
            result.put("reason", score == null ? "Exam not attempted" :
                    "Score must be ≥ 50%. Current: " + score);
        else
            result.put("reason", "Eligible for certificate");

        return result;
    }

    // ────────────────────────────────────────────────────────────────
    // GENERATE CERTIFICATE
    // ────────────────────────────────────────────────────────────────
    @Transactional
    public Certificate generateCertificateIfEligible(Long enrollmentId) {

        Map<String, Object> eligibility = checkCertificateEligibility(enrollmentId);

        if (!(Boolean) eligibility.get("eligible"))
            throw new RuntimeException("Not eligible: " + eligibility.get("reason"));

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        List<ExamAttempt> attempts =
                examAttemptRepository.findByEnrollmentIdOrderByPercentageDesc(enrollmentId);

        Double finalScore = attempts.isEmpty() ? 0.0 : attempts.get(0).getPercentage();

        Certificate certificate = new Certificate();
        certificate.setEnrollment(enrollment);
        certificate.setStudent(enrollment.getStudent());
        certificate.setCourse(enrollment.getCourse());
        certificate.setIssuedAt(LocalDateTime.now());
        certificate.setCertificateNumber(generateCertificateNumber(enrollment));
        certificate.setFinalScore(finalScore);

        return certificateRepository.save(certificate);
    }

    private String generateCertificateNumber(Enrollment enrollment) {
        return "CERT-" + enrollment.getCourse().getId() +
                "-" + enrollment.getStudent().getId() +
                "-" + System.currentTimeMillis();
    }

    // ----------------------------------------------------------------
    // HELPER METHODS
    // ----------------------------------------------------------------
    private Enrollment validateEnrollment(Long studentId, Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (!enrollment.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("Student does not match enrollment");
        }

        return enrollment;
    }

    private MaterialProgressDTO convertToDTO(MaterialProgress progress) {

        MaterialProgressDTO dto = new MaterialProgressDTO();

        dto.setId(progress.getId());
        dto.setEnrollmentId(progress.getEnrollment().getId());
        dto.setMaterialId(progress.getMaterial().getId());
        dto.setMaterialTitle(progress.getMaterial().getTitle());
        dto.setMaterialType(progress.getMaterial().getMaterialType());
        dto.setCompleted(progress.isCompleted());
        dto.setWatchTimeSeconds(progress.getWatchTimeSeconds());
        dto.setTotalDurationSeconds(progress.getTotalDurationSeconds());
        dto.setCompletionPercentage(progress.getCompletionPercentage());
        dto.setLastAccessedAt(progress.getLastAccessedAt());
        dto.setCompletedAt(progress.getCompletedAt());

        return dto;
    }
}
