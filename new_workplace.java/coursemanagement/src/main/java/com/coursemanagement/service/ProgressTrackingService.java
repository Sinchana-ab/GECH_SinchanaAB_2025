package com.coursemanagement.service;

import com.coursemanagement.dto.CertificateDTO;
import com.coursemanagement.dto.MaterialProgressDTO;
import com.coursemanagement.model.*;
import com.coursemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgressTrackingService {

    private final MaterialProgressRepository progressRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseMaterialRepository materialRepository;
    private final UserRepository userRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuizRepository quizRepository;
    private final CertificateService certificateService;

    public ProgressTrackingService(
            MaterialProgressRepository progressRepository,
            EnrollmentRepository enrollmentRepository,
            CourseMaterialRepository materialRepository,
            UserRepository userRepository,
            QuizAttemptRepository quizAttemptRepository,
            QuizRepository quizRepository,
            CertificateService certificateService) {
        this.progressRepository = progressRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.quizRepository = quizRepository;
        this.certificateService = certificateService;
    }

    /**
     * Mark a material as completed
     */
    public MaterialProgressDTO markMaterialAsComplete(Long studentId, Long materialId, Long enrollmentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        // Find or create progress record
        MaterialProgress progress = progressRepository
                .findByStudentIdAndMaterialIdAndEnrollmentId(studentId, materialId, enrollmentId)
                .orElse(new MaterialProgress());

        progress.setStudent(student);
        progress.setMaterial(material);
        progress.setEnrollment(enrollment);
        progress.setCompleted(true);
        progress.setCompletionPercentage(100.0);
        
        if (progress.getCompletedAt() == null) {
            progress.setCompletedAt(LocalDateTime.now());
        }

        MaterialProgress saved = progressRepository.save(progress);
        
        // Update overall enrollment progress
        updateEnrollmentProgress(enrollmentId);
        
        return convertToDTO(saved);
    }

    /**
     * Update video watch progress with accurate tracking
     */
    public MaterialProgressDTO updateVideoProgress(
            Long studentId, Long materialId, Long enrollmentId, 
            Integer watchTimeSeconds, Integer totalDurationSeconds) {
        
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        MaterialProgress progress = progressRepository
                .findByStudentIdAndMaterialIdAndEnrollmentId(studentId, materialId, enrollmentId)
                .orElse(new MaterialProgress());

        // Only update if new watch time is greater
        if (watchTimeSeconds > (progress.getWatchTimeSeconds() != null ? progress.getWatchTimeSeconds() : 0)) {
            progress.setStudent(student);
            progress.setMaterial(material);
            progress.setEnrollment(enrollment);
            progress.setWatchTimeSeconds(watchTimeSeconds);
            progress.setTotalDurationSeconds(totalDurationSeconds);
            
            // Calculate completion percentage
            if (totalDurationSeconds > 0) {
                double percentage = (watchTimeSeconds.doubleValue() / totalDurationSeconds) * 100;
                progress.setCompletionPercentage(Math.min(percentage, 100.0));
                
                // Auto-mark as completed if watched 90% or more
                if (percentage >= 90 && !progress.isCompleted()) {
                    progress.setCompleted(true);
                    progress.setCompletedAt(LocalDateTime.now());
                }
            }
        }

        MaterialProgress saved = progressRepository.save(progress);
        
        // Update overall enrollment progress
        updateEnrollmentProgress(enrollmentId);
        
        return convertToDTO(saved);
    }

    /**
     * Get all material progress for a student's enrollment
     */
    public List<MaterialProgressDTO> getStudentMaterialProgress(Long studentId, Long enrollmentId) {
        return progressRepository.findByStudentIdAndEnrollmentId(studentId, enrollmentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update overall enrollment progress based on completed materials AND quizzes
     * This now accounts for both materials (70%) AND quizzes (30%)
     */
    public void updateEnrollmentProgress(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Long courseId = enrollment.getCourse().getId();
        Long studentId = enrollment.getStudent().getId();

        // Get all materials for the course
        List<CourseMaterial> allMaterials = materialRepository
                .findByCourseIdOrderByOrderIndexAsc(courseId);

        // Get all quizzes for the course
        List<Quiz> allQuizzes = quizRepository.findByCourseId(courseId);

        if (allMaterials.isEmpty() && allQuizzes.isEmpty()) {
            enrollment.setProgress(0.0);
            enrollmentRepository.save(enrollment);
            return;
        }

        // Count completed materials
        Long completedMaterials = progressRepository.countCompletedMaterialsByEnrollment(enrollmentId);
        
        // Count passed quizzes using the FIXED method
        List<QuizAttempt> quizAttempts = quizAttemptRepository
            .findByStudentIdAndQuizCourseId(studentId, courseId);
        
        long passedQuizzes = quizAttempts.stream()
            .filter(QuizAttempt::isPassed)
            .map(qa -> qa.getQuiz().getId())
            .distinct()
            .count();

        // Calculate weighted progress
        int totalItems = allMaterials.size() + allQuizzes.size();
        if (totalItems == 0) {
            enrollment.setProgress(0.0);
            enrollmentRepository.save(enrollment);
            return;
        }

        // Materials: 70% weight, Quizzes: 30% weight
        double materialWeight = 0.7;
        double quizWeight = 0.3;
        
        double materialProgress = allMaterials.isEmpty() ? 0 : 
            (completedMaterials.doubleValue() / allMaterials.size()) * 100 * materialWeight;
        
        double quizProgress = allQuizzes.isEmpty() ? 0 : 
            (passedQuizzes / (double) allQuizzes.size()) * 100 * quizWeight;
        
        double totalProgress = materialProgress + quizProgress;
        
        // If no quizzes exist, materials are 100% of progress
        if (allQuizzes.isEmpty()) {
            totalProgress = allMaterials.isEmpty() ? 0 : 
                (completedMaterials.doubleValue() / allMaterials.size()) * 100;
        }
        
        enrollment.setProgress(Math.min(totalProgress, 100.0));
        
        // Update status based on progress
        if (totalProgress == 0) {
            enrollment.setStatus("ENROLLED");
        } else if (totalProgress < 100) {
            enrollment.setStatus("IN_PROGRESS");
        } else {
            enrollment.setStatus("COMPLETED");
            if (enrollment.getCompletedAt() == null) {
                enrollment.setCompletedAt(LocalDateTime.now());
            }
        }
        
        enrollmentRepository.save(enrollment);
    }

    /**
     * FIXED: Check if student is eligible for certificate
     * Requirements:
     * 1. All materials completed (100%)
     * 2. All quizzes passed
     * 3. Course is marked as COMPLETED
     */
    public Map<String, Object> checkCertificateEligibility(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Map<String, Object> result = new HashMap<>();
        
        Long courseId = enrollment.getCourse().getId();
        Long studentId = enrollment.getStudent().getId();

        // Check materials completion
        List<CourseMaterial> allMaterials = materialRepository
                .findByCourseIdOrderByOrderIndexAsc(courseId);
        Long completedMaterials = progressRepository.countCompletedMaterialsByEnrollment(enrollmentId);
        
        boolean allMaterialsCompleted = allMaterials.size() == completedMaterials.intValue();
        
        // Check quizzes - FIXED to use correct method
        List<Quiz> allQuizzes = quizRepository.findByCourseId(courseId);
        List<QuizAttempt> studentAttempts = quizAttemptRepository
            .findByStudentIdAndQuizCourseId(studentId, courseId);
        
        // Get unique passed quizzes
        long passedQuizCount = studentAttempts.stream()
                .filter(QuizAttempt::isPassed)
                .map(qa -> qa.getQuiz().getId())
                .distinct()
                .count();
        
        boolean allQuizzesPassed = allQuizzes.size() == passedQuizCount;
        
        // Check course completion
        boolean courseCompleted = "COMPLETED".equals(enrollment.getStatus()) && 
                                  enrollment.getProgress() >= 100.0;
        
        // Overall eligibility
        boolean eligible = allMaterialsCompleted && allQuizzesPassed && courseCompleted;
        
        // Calculate final score (average of quiz scores for passed quizzes)
        double finalScore = 0.0;
        if (!studentAttempts.isEmpty()) {
            // Get best score for each quiz
            Map<Long, Double> bestScores = studentAttempts.stream()
                    .filter(QuizAttempt::isPassed)
                    .collect(Collectors.groupingBy(
                            qa -> qa.getQuiz().getId(),
                            Collectors.averagingDouble(QuizAttempt::getPercentage)
                    ));
            
            if (!bestScores.isEmpty()) {
                finalScore = bestScores.values().stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);
            }
        }
        
        // If no quizzes, use 100% as final score
        if (allQuizzes.isEmpty() && allMaterialsCompleted) {
            finalScore = 100.0;
        }
        
        result.put("eligible", eligible);
        result.put("allMaterialsCompleted", allMaterialsCompleted);
        result.put("completedMaterials", completedMaterials);
        result.put("totalMaterials", allMaterials.size());
        result.put("allQuizzesPassed", allQuizzesPassed);
        result.put("passedQuizzes", passedQuizCount);
        result.put("totalQuizzes", allQuizzes.size());
        result.put("courseCompleted", courseCompleted);
        result.put("finalScore", finalScore);
        result.put("progress", enrollment.getProgress());
        
        return result;
    }

    /**
     * Generate certificate if student is eligible
     */
    public CertificateDTO generateCertificateIfEligible(Long enrollmentId) {
        Map<String, Object> eligibility = checkCertificateEligibility(enrollmentId);
        
        if (!(boolean) eligibility.get("eligible")) {
            StringBuilder message = new StringBuilder("Not eligible for certificate. Requirements:\n");
            
            if (!(boolean) eligibility.get("allMaterialsCompleted")) {
                message.append("✗ Complete all course materials (")
                       .append(eligibility.get("completedMaterials"))
                       .append("/")
                       .append(eligibility.get("totalMaterials"))
                       .append(")\n");
            }
            
            if (!(boolean) eligibility.get("allQuizzesPassed")) {
                message.append("✗ Pass all quizzes (")
                       .append(eligibility.get("passedQuizzes"))
                       .append("/")
                       .append(eligibility.get("totalQuizzes"))
                       .append(")\n");
            }
            
            if (!(boolean) eligibility.get("courseCompleted")) {
                message.append("✗ Course must be marked as completed\n");
            }
            
            throw new RuntimeException(message.toString());
        }

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Double finalScore = (Double) eligibility.get("finalScore");
        
        // Generate certificate
        CertificateDTO certificate = certificateService.generateCertificate(
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId(),
                finalScore
        );
        
        return certificate;
    }

    /**
     * Get detailed progress report for a student
     */
    public Map<String, Object> getDetailedProgressReport(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Map<String, Object> report = new HashMap<>();
        
        Long courseId = enrollment.getCourse().getId();
        Long studentId = enrollment.getStudent().getId();

        // Material progress
        List<CourseMaterial> allMaterials = materialRepository
                .findByCourseIdOrderByOrderIndexAsc(courseId);
        List<MaterialProgress> materialProgress = progressRepository
                .findByStudentIdAndEnrollmentId(studentId, enrollmentId);
        
        // Quiz progress - FIXED
        List<Quiz> allQuizzes = quizRepository.findByCourseId(courseId);
        List<QuizAttempt> quizAttempts = quizAttemptRepository
                .findByStudentIdAndQuizCourseId(studentId, courseId);
        
        report.put("overallProgress", enrollment.getProgress());
        report.put("status", enrollment.getStatus());
        report.put("enrolledAt", enrollment.getEnrolledAt());
        report.put("completedAt", enrollment.getCompletedAt());
        
        // Materials
        report.put("totalMaterials", allMaterials.size());
        report.put("completedMaterials", materialProgress.stream().filter(MaterialProgress::isCompleted).count());
        report.put("materialDetails", materialProgress.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        
        // Quizzes
        report.put("totalQuizzes", allQuizzes.size());
        report.put("passedQuizzes", quizAttempts.stream()
                .filter(QuizAttempt::isPassed)
                .map(qa -> qa.getQuiz().getId())
                .distinct()
                .count());
        report.put("quizAttempts", quizAttempts.size());
        
        // Certificate
        report.put("certificateIssued", enrollment.isCertificateIssued());
        report.put("certificateUrl", enrollment.getCertificateUrl());
        
        // Time spent
        long totalWatchTime = materialProgress.stream()
                .mapToLong(mp -> mp.getWatchTimeSeconds() != null ? mp.getWatchTimeSeconds() : 0)
                .sum();
        report.put("totalWatchTimeMinutes", totalWatchTime / 60);
        
        return report;
    }

    private MaterialProgressDTO convertToDTO(MaterialProgress progress) {
        MaterialProgressDTO dto = new MaterialProgressDTO();
        dto.setId(progress.getId());
        dto.setStudentId(progress.getStudent().getId());
        dto.setEnrollmentId(progress.getEnrollment().getId());
        dto.setMaterialId(progress.getMaterial().getId());
        dto.setMaterialTitle(progress.getMaterial().getTitle());
        dto.setMaterialType(progress.getMaterial().getMaterialType());
        dto.setCompleted(progress.isCompleted());
        dto.setWatchTimeSeconds(progress.getWatchTimeSeconds());
        dto.setTotalDurationSeconds(progress.getTotalDurationSeconds());
        dto.setCompletionPercentage(progress.getCompletionPercentage());
        dto.setStartedAt(progress.getStartedAt());
        dto.setCompletedAt(progress.getCompletedAt());
        dto.setLastAccessedAt(progress.getLastAccessedAt());
        return dto;
    }
}