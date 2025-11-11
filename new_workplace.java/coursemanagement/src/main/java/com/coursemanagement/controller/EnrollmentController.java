package com.coursemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.EnrollmentDTO;
import com.coursemanagement.dto.QuizAttemptDTO;
import com.coursemanagement.service.EnrollmentService;
import com.coursemanagement.service.QuizService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse> enrollInCourse(@RequestBody Map<String, Long> request) {
        try {
            Long studentId = request.get("studentId");
            Long courseId = request.get("courseId");
            EnrollmentDTO enrollment = enrollmentService.enrollStudent(studentId, courseId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Enrolled successfully", enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/enrollments")
    public ResponseEntity<ApiResponse> getStudentEnrollments(@RequestParam Long studentId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.getStudentEnrollments(studentId);
            return ResponseEntity.ok(new ApiResponse(true, "Enrollments retrieved successfully", enrollments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/enrollments/{id}/progress")
    public ResponseEntity<ApiResponse> updateProgress(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        try {
            Double progress = request.get("progress");
            EnrollmentDTO enrollment = enrollmentService.updateProgress(id, progress);
            return ResponseEntity.ok(new ApiResponse(true, "Progress updated successfully", enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/enrollments/course/{courseId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getCourseEnrollments(@PathVariable Long courseId) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.getCourseEnrollments(courseId);
            return ResponseEntity.ok(new ApiResponse(true, "Course enrollments retrieved", enrollments));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}

@RestController
@RequestMapping("/api/student/quiz")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse> startQuiz(@RequestBody Map<String, Long> request) {
        try {
            Long quizId = request.get("quizId");
            Long studentId = request.get("studentId");
            QuizAttemptDTO attempt = quizService.startQuizAttempt(quizId, studentId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Quiz started successfully", attempt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/submit/{attemptId}")
    public ResponseEntity<ApiResponse> submitQuiz(
            @PathVariable Long attemptId,
            @RequestBody Map<String, Object> request) {
        try {
            String answers = (String) request.get("answers");
            Integer score = (Integer) request.get("score");
            Integer totalPoints = (Integer) request.get("totalPoints");
            
            QuizAttemptDTO attempt = quizService.submitQuizAttempt(attemptId, answers, score, totalPoints);
            return ResponseEntity.ok(new ApiResponse(true, "Quiz submitted successfully", attempt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/attempts")
    public ResponseEntity<ApiResponse> getAttempts(
            @RequestParam Long studentId,
            @RequestParam Long quizId) {
        try {
            List<QuizAttemptDTO> attempts = quizService.getStudentAttempts(studentId, quizId);
            return ResponseEntity.ok(new ApiResponse(true, "Attempts retrieved successfully", attempts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}