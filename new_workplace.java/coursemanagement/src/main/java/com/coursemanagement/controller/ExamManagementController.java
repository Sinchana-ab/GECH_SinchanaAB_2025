package com.coursemanagement.controller;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.ExamDTO;
import com.coursemanagement.dto.ExamQuestionDTO;
import com.coursemanagement.model.*;
import com.coursemanagement.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper; // ADDED: Import ObjectMapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ExamManagementController {

    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private ExamQuestionRepository questionRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired // ADDED: Inject ObjectMapper
    private ObjectMapper objectMapper;

    // The constructor is no longer needed since you are using @Autowired fields.

    // ===== CREATE EXAM =====
    @PostMapping("/instructor/exams")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> createExam(@RequestBody Map<String, Object> request) {
        try {
            Long courseId = Long.valueOf(request.get("courseId").toString());
            Long instructorId = Long.valueOf(request.get("instructorId").toString());

            // Validate course ownership
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            User instructor = userRepository.findById(instructorId)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));

            // Check if instructor owns the course (unless admin)
            boolean isAdmin = instructor.getRole().equals("ROLE_ADMIN");
            if (!isAdmin && !course.getInstructor().getId().equals(instructorId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You can only create exams for your own courses", null));
            }

            // Create exam
            Exam exam = new Exam();
            exam.setCourse(course);
            exam.setTitle(request.get("title").toString());
            exam.setDescription(request.get("description").toString());
            exam.setTimeLimitMinutes(Integer.parseInt(request.get("timeLimitMinutes").toString()));
            exam.setPassingScore(Integer.parseInt(request.get("passingScore").toString()));
            exam.setMaxAttempts(Integer.parseInt(request.get("maxAttempts").toString()));
            exam.setPublished(false);
            exam.setFinalExam(true);

            Exam savedExam = examRepository.save(exam);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Exam created successfully", convertToDTO(savedExam)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to create exam: " + e.getMessage(), null));
        }
    }

    // ===== UPDATE EXAM (No changes needed here) =====
    @PutMapping("/instructor/exams/{examId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> updateExam(
            @PathVariable Long examId,
            @RequestBody Map<String, Object> request) {
        try {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            exam.setTitle(request.get("title").toString());
            exam.setDescription(request.get("description").toString());
            exam.setTimeLimitMinutes(Integer.parseInt(request.get("timeLimitMinutes").toString()));
            exam.setPassingScore(Integer.parseInt(request.get("passingScore").toString()));
            exam.setMaxAttempts(Integer.parseInt(request.get("maxAttempts").toString()));

            Exam updatedExam = examRepository.save(exam);

            return ResponseEntity.ok(new ApiResponse(true, "Exam updated successfully", convertToDTO(updatedExam)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to update exam: " + e.getMessage(), null));
        }
    }

    // ===== ADD QUESTION (FIXED JSON SERIALIZATION) =====
    @PostMapping("/instructor/exams/{examId}/questions")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> addQuestion(
            @PathVariable Long examId,
            @RequestBody Map<String, Object> request) {
        try {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            ExamQuestion question = new ExamQuestion();
            question.setExam(exam);
            question.setQuestionText(request.get("questionText").toString());
            question.setQuestionType(request.get("questionType").toString());
            question.setCorrectAnswer(request.get("correctAnswer").toString());
            question.setMarks(Integer.parseInt(request.get("marks").toString()));
            question.setOrderIndex(Integer.parseInt(request.get("orderIndex").toString()));

            // FIX APPLIED HERE: Use ObjectMapper to convert the List to a valid JSON String
            if (request.containsKey("options") && request.get("options") != null) {
                Object optionsValue = request.get("options");
                if (optionsValue instanceof List) {
                    // Convert Java List to a valid JSON string (e.g., '["A", "B"]')
                    String optionsJson = objectMapper.writeValueAsString(optionsValue);
                    question.setOptions(optionsJson);
                } else {
                    // Fallback if it's not a list, though it should be for multiple choice
                    question.setOptions(optionsValue.toString());
                }
            }
            if (request.containsKey("explanation") && request.get("explanation") != null) {
                question.setExplanation(request.get("explanation").toString());
            }

            ExamQuestion savedQuestion = questionRepository.save(question);

            // Update total marks
            updateExamTotalMarks(exam);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Question added successfully", convertQuestionToDTO(savedQuestion)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to add question: " + e.getMessage(), null));
        }
    }

    // ===== UPDATE QUESTION (FIXED JSON SERIALIZATION) =====
    @PutMapping("/instructor/exams/questions/{questionId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody Map<String, Object> request) {
        try {
            ExamQuestion question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            question.setQuestionText(request.get("questionText").toString());
            question.setQuestionType(request.get("questionType").toString());
            question.setCorrectAnswer(request.get("correctAnswer").toString());
            question.setMarks(Integer.parseInt(request.get("marks").toString()));
            question.setOrderIndex(Integer.parseInt(request.get("orderIndex").toString()));

            // FIX APPLIED HERE: Use ObjectMapper to convert the List to a valid JSON String
            if (request.containsKey("options") && request.get("options") != null) {
                Object optionsValue = request.get("options");
                if (optionsValue instanceof List) {
                    // Convert Java List to a valid JSON string (e.g., '["A", "B"]')
                    String optionsJson = objectMapper.writeValueAsString(optionsValue);
                    question.setOptions(optionsJson);
                } else {
                    // Fallback
                    question.setOptions(optionsValue.toString());
                }
            }
            if (request.containsKey("explanation") && request.get("explanation") != null) {
                question.setExplanation(request.get("explanation").toString());
            }

            ExamQuestion updatedQuestion = questionRepository.save(question);

            // Update total marks
            updateExamTotalMarks(question.getExam());

            return ResponseEntity.ok(new ApiResponse(true, "Question updated successfully", convertQuestionToDTO(updatedQuestion)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to update question: " + e.getMessage(), null));
        }
    }

    // ===== DELETE QUESTION (No changes needed here) =====
    @DeleteMapping("/instructor/exams/questions/{questionId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long questionId) {
        try {
            ExamQuestion question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            Exam exam = question.getExam();
            questionRepository.deleteById(questionId);

            // Update total marks
            updateExamTotalMarks(exam);

            return ResponseEntity.ok(new ApiResponse(true, "Question deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to delete question: " + e.getMessage(), null));
        }
    }

    // ===== GET EXAM WITH QUESTIONS (No changes needed here) =====
    @GetMapping("/instructor/exams/{examId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getExamWithQuestions(@PathVariable Long examId) {
        try {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            List<ExamQuestion> questions = questionRepository.findByExamIdOrderByOrderIndexAsc(examId);

            Map<String, Object> result = new HashMap<>();
            result.put("exam", convertToDTO(exam));
            result.put("questions", questions.stream()
                    .map(this::convertQuestionToDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(new ApiResponse(true, "Exam retrieved successfully", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Exam not found: " + e.getMessage(), null));
        }
    }

    // ===== GET ALL EXAMS FOR INSTRUCTOR (No changes needed here) =====
    @GetMapping("/instructor/exams")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getInstructorExams(@RequestParam Long instructorId) {
        try {
            User instructor = userRepository.findById(instructorId)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));

            List<Exam> exams;
            if (instructor.getRole().equals("ROLE_ADMIN")) {
                exams = examRepository.findAll();
            } else {
                List<Course> instructorCourses = courseRepository.findByInstructorId(instructorId);
                exams = new ArrayList<>();
                for (Course course : instructorCourses) {
                    exams.addAll(examRepository.findByCourseIdOrderByCreatedAtDesc(course.getId()));
                }
            }

            List<Map<String, Object>> examDTOs = exams.stream()
                    .map(exam -> {
                        Map<String, Object> dto = new HashMap<>();
                        dto.put("exam", convertToDTO(exam));
                        dto.put("questionCount", questionRepository.countByExamId(exam.getId()));
                        return dto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "Exams retrieved successfully", examDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve exams: " + e.getMessage(), null));
        }
    }

    // ===== PUBLISH/UNPUBLISH EXAM (No changes needed here) =====
    @PutMapping("/instructor/exams/{examId}/publish")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> togglePublish(
            @PathVariable Long examId,
            @RequestBody Map<String, Boolean> request) {
        try {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            Long questionCount = questionRepository.countByExamId(examId);
            if (questionCount == null || questionCount == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Cannot publish exam without questions", null));
            }

            exam.setPublished(request.get("published"));
            Exam updatedExam = examRepository.save(exam);

            String message = updatedExam.isPublished() ? "Exam published successfully" : "Exam unpublished";
            return ResponseEntity.ok(new ApiResponse(true, message, convertToDTO(updatedExam)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to update exam: " + e.getMessage(), null));
        }
    }

    // ===== DELETE EXAM (No changes needed here) =====
    @DeleteMapping("/instructor/exams/{examId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> deleteExam(@PathVariable Long examId) {
        try {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            examRepository.delete(exam);

            return ResponseEntity.ok(new ApiResponse(true, "Exam deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to delete exam: " + e.getMessage(), null));
        }
    }

    // ===== ADMIN: GET ALL EXAMS (No changes needed here) =====
    @GetMapping("/admin/exams")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllExams() {
        try {
            List<Exam> exams = examRepository.findAll();
            
            List<Map<String, Object>> examDTOs = exams.stream()
                    .map(exam -> {
                        Map<String, Object> dto = new HashMap<>();
                        dto.put("exam", convertToDTO(exam));
                        dto.put("questionCount", questionRepository.countByExamId(exam.getId()));
                        return dto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse(true, "All exams retrieved successfully", examDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve exams: " + e.getMessage(), null));
        }
    }

    // ===== HELPER METHODS (No changes needed here) =====
    private void updateExamTotalMarks(Exam exam) {
        List<ExamQuestion> questions = questionRepository.findByExamIdOrderByOrderIndexAsc(exam.getId());
        int totalMarks = questions.stream()
                .mapToInt(q -> q.getMarks() != null ? q.getMarks() : 0)
                .sum();
        exam.setTotalMarks(totalMarks);
        examRepository.save(exam);
    }

    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setCourseId(exam.getCourse().getId());
        dto.setCourseTitle(exam.getCourse().getTitle());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setTimeLimitMinutes(exam.getTimeLimitMinutes());
        dto.setPassingScore(exam.getPassingScore());
        dto.setMaxAttempts(exam.getMaxAttempts());
        dto.setTotalMarks(exam.getTotalMarks());
        dto.setPublished(exam.isPublished());
        dto.setFinalExam(exam.isFinalExam());
        
        // Count questions
        Long questionCount = questionRepository.countByExamId(exam.getId());
        dto.setQuestionCount(questionCount != null ? questionCount.intValue() : 0);
        
        return dto;
    }

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
        
        // NOTE: correctAnswer is intentionally NOT included for security
        
        return dto;
    }
}