package com.coursemanagement.service;

import com.coursemanagement.dto.ExamAttemptDTO;
import com.coursemanagement.dto.ExamDTO;
import com.coursemanagement.model.*;
import com.coursemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EmailService emailService;

    public ExamService(ExamRepository examRepository,
                      ExamAttemptRepository examAttemptRepository,
                      ExamQuestionRepository examQuestionRepository,
                      UserRepository userRepository,
                      EnrollmentRepository enrollmentRepository,
                      EmailService emailService) {
        this.examRepository = examRepository;
        this.examAttemptRepository = examAttemptRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.emailService = emailService;
    }

    /**
     * Get final exam for a course
     */
    public ExamDTO getCourseExam(Long courseId) {
        Exam exam = examRepository.findByCourseIdAndIsFinalExamTrue(courseId)
                .orElseThrow(() -> new RuntimeException("No final exam found for this course"));
        return convertToDTO(exam);
    }

    /**
     * Start exam attempt
     */
    public ExamAttemptDTO startExamAttempt(Long examId, Long studentId, Long enrollmentId) {
        // Validate exam exists
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        // Validate student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Validate enrollment
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        // Check if student is enrolled in the course
        if (!enrollment.getStudent().getId().equals(studentId) || 
            !enrollment.getCourse().getId().equals(exam.getCourse().getId())) {
            throw new RuntimeException("Student is not enrolled in this course");
        }

        // Check max attempts
        Long attemptCount = examAttemptRepository.countByStudentIdAndExamId(studentId, examId);
        if (exam.getMaxAttempts() != null && attemptCount >= exam.getMaxAttempts()) {
            throw new RuntimeException("Maximum attempts exceeded for this exam");
        }

        // Check if there's an in-progress attempt
        List<ExamAttempt> inProgressAttempts = examAttemptRepository
                .findByStudentIdAndStatus(studentId, "IN_PROGRESS");
        
        for (ExamAttempt attempt : inProgressAttempts) {
            if (attempt.getExam().getId().equals(examId)) {
                throw new RuntimeException("You have an exam in progress. Please complete it first.");
            }
        }

        // Create new attempt
        ExamAttempt attempt = new ExamAttempt();
        attempt.setExam(exam);
        attempt.setStudent(student);
        attempt.setEnrollment(enrollment);
        attempt.setAttemptNumber(attemptCount.intValue() + 1);
        attempt.setStatus("IN_PROGRESS");
        attempt.setTotalMarks(exam.getTotalMarks());

        ExamAttempt savedAttempt = examAttemptRepository.save(attempt);
        return convertAttemptToDTO(savedAttempt);
    }

    /**
     * Submit exam and calculate score
     */
    public ExamAttemptDTO submitExamAttempt(Long attemptId, String answers) {
        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Exam attempt not found"));

        if (!"IN_PROGRESS".equals(attempt.getStatus())) {
            throw new RuntimeException("This exam has already been submitted");
        }

        // Calculate score
        int score = calculateScore(attempt.getExam(), answers);
        
        // Update attempt
        attempt.setAnswers(answers);
        attempt.setScore(score);
        attempt.setSubmittedAt(LocalDateTime.now());
        attempt.setStatus("COMPLETED");
        
        // Calculate time taken
        if (attempt.getStartedAt() != null) {
            long minutesTaken = java.time.Duration.between(
                attempt.getStartedAt(), 
                LocalDateTime.now()
            ).toMinutes();
            attempt.setTimeTakenMinutes((int) minutesTaken);
        }

        ExamAttempt savedAttempt = examAttemptRepository.save(attempt);
        
        // Send result email
        emailService.sendQuizResult(convertToQuizAttempt(savedAttempt));
        
        return convertAttemptToDTO(savedAttempt);
    }

    /**
     * Get student's exam attempts
     */
    public List<ExamAttemptDTO> getStudentAttempts(Long studentId, Long examId) {
        return examAttemptRepository.findByStudentIdAndExamIdOrderByAttemptNumberDesc(studentId, examId)
                .stream()
                .map(this::convertAttemptToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get best attempt for certificate eligibility
     */
    public ExamAttemptDTO getBestAttempt(Long enrollmentId) {
        List<ExamAttempt> attempts = examAttemptRepository
                .findByEnrollmentIdOrderByPercentageDesc(enrollmentId);
        
        if (attempts.isEmpty()) {
            return null;
        }
        
        return convertAttemptToDTO(attempts.get(0));
    }

    /**
     * Calculate exam score based on answers
     */
    private int calculateScore(Exam exam, String answersJson) {
        List<ExamQuestion> questions = examQuestionRepository
                .findByExamIdOrderByOrderIndexAsc(exam.getId());
        
        int totalScore = 0;
        
        // Parse answers JSON (simple format: {"1": "answer1", "2": "answer2"})
        // In production, use a proper JSON library
        
        for (ExamQuestion question : questions) {
            String questionId = question.getId().toString();
            
            // Extract answer from JSON (simplified - use Jackson in production)
            if (answersJson.contains("\"" + questionId + "\"")) {
                String studentAnswer = extractAnswer(answersJson, questionId);
                
                // Check if answer is correct
                if (isAnswerCorrect(question, studentAnswer)) {
                    totalScore += question.getMarks();
                }
            }
        }
        
        return totalScore;
    }

    /**
     * Extract answer from JSON string (simplified)
     */
    private String extractAnswer(String json, String questionId) {
        try {
            String pattern = "\"" + questionId + "\"\\s*:\\s*\"([^\"]+)\"";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            // Log error
        }
        return "";
    }

    /**
     * Check if answer is correct
     */
    private boolean isAnswerCorrect(ExamQuestion question, String studentAnswer) {
        if (studentAnswer == null || studentAnswer.trim().isEmpty()) {
            return false;
        }
        
        String correctAnswer = question.getCorrectAnswer().trim();
        String givenAnswer = studentAnswer.trim();
        
        // For multiple choice and true/false - exact match
        if ("MULTIPLE_CHOICE".equals(question.getQuestionType()) || 
            "TRUE_FALSE".equals(question.getQuestionType())) {
            return correctAnswer.equalsIgnoreCase(givenAnswer);
        }
        
        // For short answer - case insensitive match
        if ("SHORT_ANSWER".equals(question.getQuestionType())) {
            return correctAnswer.equalsIgnoreCase(givenAnswer);
        }
        
        return false;
    }

    /**
     * Convert to QuizAttempt for email service (temporary adapter)
     */
    private QuizAttempt convertToQuizAttempt(ExamAttempt examAttempt) {
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setStudent(examAttempt.getStudent());
        
        // Create a mock quiz from exam
        Quiz quiz = new Quiz();
        quiz.setTitle(examAttempt.getExam().getTitle());
        quiz.setPassingScore(examAttempt.getExam().getPassingScore());
        quizAttempt.setQuiz(quiz);
        
        quizAttempt.setScore(examAttempt.getScore());
        quizAttempt.setTotalPoints(examAttempt.getTotalMarks());
        quizAttempt.setPercentage(examAttempt.getPercentage());
        quizAttempt.setPassed(examAttempt.isPassed());
        
        return quizAttempt;
    }

    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setCourseId(exam.getCourse().getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setTimeLimitMinutes(exam.getTimeLimitMinutes());
        dto.setPassingScore(exam.getPassingScore());
        dto.setMaxAttempts(exam.getMaxAttempts());
        dto.setTotalMarks(exam.getTotalMarks());
        dto.setPublished(exam.isPublished());
        dto.setFinalExam(exam.isFinalExam());
        return dto;
    }

    private ExamAttemptDTO convertAttemptToDTO(ExamAttempt attempt) {
        ExamAttemptDTO dto = new ExamAttemptDTO();
        dto.setId(attempt.getId());
        dto.setExamId(attempt.getExam().getId());
        dto.setExamTitle(attempt.getExam().getTitle());
        dto.setStudentId(attempt.getStudent().getId());
        dto.setStudentName(attempt.getStudent().getName());
        dto.setEnrollmentId(attempt.getEnrollment().getId());
        dto.setStartedAt(attempt.getStartedAt());
        dto.setSubmittedAt(attempt.getSubmittedAt());
        dto.setScore(attempt.getScore());
        dto.setTotalMarks(attempt.getTotalMarks());
        dto.setPercentage(attempt.getPercentage());
        dto.setPassed(attempt.isPassed());
        dto.setAttemptNumber(attempt.getAttemptNumber());
        dto.setStatus(attempt.getStatus());
        dto.setTimeTakenMinutes(attempt.getTimeTakenMinutes());
        return dto;
    }
}