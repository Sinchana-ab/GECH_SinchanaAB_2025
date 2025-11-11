package com.coursemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.MaterialProgressDTO;
import com.coursemanagement.service.ProgressTrackingService;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
public class ProgressTrackingController {

    private final ProgressTrackingService progressService;

    public ProgressTrackingController(ProgressTrackingService progressService) {
        this.progressService = progressService;
    }

    /**
     * Mark a material as completed
     */
    @PostMapping("/materials/{materialId}/complete")
    public ResponseEntity<ApiResponse> markMaterialComplete(
            @PathVariable Long materialId,
            @RequestParam Long studentId,
            @RequestParam Long enrollmentId) {
        try {
            MaterialProgressDTO progress = progressService.markMaterialAsComplete(
                studentId, materialId, enrollmentId);
            return ResponseEntity.ok(new ApiResponse(true, "Material marked as complete", progress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Update video watch time
     */
    @PostMapping("/materials/{materialId}/watch-time")
    public ResponseEntity<ApiResponse> updateWatchTime(
            @PathVariable Long materialId,
            @RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.valueOf(request.get("studentId").toString());
            Long enrollmentId = Long.valueOf(request.get("enrollmentId").toString());
            Integer watchTimeSeconds = Integer.valueOf(request.get("watchTimeSeconds").toString());
            Integer totalDurationSeconds = Integer.valueOf(request.get("totalDurationSeconds").toString());

            MaterialProgressDTO progress = progressService.updateVideoProgress(
                studentId, materialId, enrollmentId, watchTimeSeconds, totalDurationSeconds);
            
            return ResponseEntity.ok(new ApiResponse(true, "Watch time updated", progress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Get material progress for a student
     */
    @GetMapping("/materials/progress")
    public ResponseEntity<ApiResponse> getMaterialProgress(
            @RequestParam Long studentId,
            @RequestParam Long enrollmentId) {
        try {
            var progressList = progressService.getStudentMaterialProgress(studentId, enrollmentId);
            return ResponseEntity.ok(new ApiResponse(true, "Progress retrieved", progressList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Check if student can get certificate
     */
    @GetMapping("/enrollments/{enrollmentId}/certificate-eligibility")
    public ResponseEntity<ApiResponse> checkCertificateEligibility(@PathVariable Long enrollmentId) {
        try {
            Map<String, Object> eligibility = progressService.checkCertificateEligibility(enrollmentId);
            return ResponseEntity.ok(new ApiResponse(true, "Eligibility checked", eligibility));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Request certificate generation
     */
    @PostMapping("/enrollments/{enrollmentId}/request-certificate")
    public ResponseEntity<ApiResponse> requestCertificate(@PathVariable Long enrollmentId) {
        try {
            var certificate = progressService.generateCertificateIfEligible(enrollmentId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Certificate generated successfully", certificate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}