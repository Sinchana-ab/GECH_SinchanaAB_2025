package com.coursemanagement.controller;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.ExamAttemptDTO;
import com.coursemanagement.dto.ExamDTO;
import com.coursemanagement.dto.ExamQuestionDTO;
import com.coursemanagement.model.ExamQuestion;
import com.coursemanagement.repository.ExamQuestionRepository;
import com.coursemanagement.service.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ExamController {

    private final ExamService examService;
    
    @Autowired
    private ExamQuestionRepository examQuestionRepository;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * ✅ PUBLIC: Get final exam for a course
     */
    @GetMapping("/public/courses/{courseId}/exam")
    public ResponseEntity<ApiResponse> getCourseExam(@PathVariable Long courseId) {
        try {
            ExamDTO exam = examService.getCourseExam(courseId);
            return ResponseEntity.ok(new ApiResponse(true, "Exam retrieved successfully", exam));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * ✅ PUBLIC: Get exam questions (for students taking exam)
     */
    @GetMapping("/public/exams/{examId}/questions")
    public ResponseEntity<ApiResponse> getExamQuestions(@PathVariable Long examId) {
        try {
            List<ExamQuestion> questions = examQuestionRepository
                    .findByExamIdOrderByOrderIndexAsc(examId);
            
            List<ExamQuestionDTO> questionDTOs = questions.stream()
                    .map(this::convertQuestionToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(new ApiResponse(true, "Questions retrieved", questionDTOs));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to get questions: " + e.getMessage(), null));
        }
    }

    /**
     * ✅ STUDENT: Start exam attempt
     */
    @PostMapping("/student/exams/start")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> startExam(@RequestBody Map<String, Object> request) {
        try {
            Long examId = Long.valueOf(request.get("examId").toString());
            Long studentId = Long.valueOf(request.get("studentId").toString());
            Long enrollmentId = Long.valueOf(request.get("enrollmentId").toString());

            ExamAttemptDTO attempt = examService.startExamAttempt(examId, studentId, enrollmentId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Exam started successfully", attempt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * ✅ STUDENT: Submit exam
     */
    @PostMapping("/student/exams/submit/{attemptId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> submitExam(
            @PathVariable Long attemptId,
            @RequestBody Map<String, String> request) {
        try {
            String answers = request.get("answers");
            ExamAttemptDTO attempt = examService.submitExamAttempt(attemptId, answers);
            return ResponseEntity.ok(new ApiResponse(true, "Exam submitted successfully", attempt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * ✅ STUDENT: Get exam attempts
     */
    @GetMapping("/student/exams/attempts")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getAttempts(
            @RequestParam Long studentId,
            @RequestParam Long examId) {
        try {
            List<ExamAttemptDTO> attempts = examService.getStudentAttempts(studentId, examId);
            return ResponseEntity.ok(new ApiResponse(true, "Attempts retrieved", attempts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * ✅ STUDENT: Get best attempt (for certificate)
     */
    @GetMapping("/student/exams/best-attempt/{enrollmentId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getBestAttempt(@PathVariable Long enrollmentId) {
        try {
            ExamAttemptDTO attempt = examService.getBestAttempt(enrollmentId);
            return ResponseEntity.ok(new ApiResponse(true, "Best attempt retrieved", attempt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
    
    // ===== HELPER METHOD =====
    /**
     * Convert ExamQuestion to DTO (without correct answer for security)
     */
    private ExamQuestionDTO convertQuestionToDTO(ExamQuestion question) {
        ExamQuestionDTO dto = new ExamQuestionDTO();
        dto.setId(question.getId());
        dto.setExamId(question.getExam().getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setQuestionType(question.getQuestionType());
        dto.setOptions(question.getOptions());
        dto.setMarks(question.getMarks());
        dto.setOrderIndex(question.getOrderIndex());
        dto.setExplanation(question.getExplanation());
        
        // ✅ NOTE: We DO NOT include correctAnswer for security
        // Students should not see the correct answer when taking exam
        
        return dto;
    }
}