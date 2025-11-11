package com.coursemanagement.service;

import com.coursemanagement.dto.QuizAttemptDTO;
import com.coursemanagement.model.*;
import com.coursemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final UserRepository userRepository;
    private final EmailService emailService; // Added

    public QuizService(QuizRepository quizRepository, QuizAttemptRepository quizAttemptRepository,
                      UserRepository userRepository, EmailService emailService) { // Updated
        this.quizRepository = quizRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public QuizAttemptDTO startQuizAttempt(Long quizId, Long studentId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Long attemptCount = quizAttemptRepository.countByStudentIdAndQuizId(studentId, quizId);
        if (quiz.getMaxAttempts() != null && attemptCount >= quiz.getMaxAttempts()) {
            throw new RuntimeException("Maximum attempts exceeded");
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setStudent(student);
        attempt.setStatus("IN_PROGRESS");

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);
        return convertAttemptToDTO(savedAttempt);
    }

    public QuizAttemptDTO submitQuizAttempt(Long attemptId, String answers, Integer score, Integer totalPoints) {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Quiz attempt not found"));

        attempt.setAnswers(answers);
        attempt.setScore(score);
        attempt.setTotalPoints(totalPoints);
        attempt.setPercentage((double) score / totalPoints * 100);
        attempt.setCompletedAt(LocalDateTime.now());
        attempt.setStatus("COMPLETED");
        
        Quiz quiz = attempt.getQuiz();
        attempt.setPassed(attempt.getPercentage() >= quiz.getPassingScore());

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);
        
        // Send quiz result email
        emailService.sendQuizResult(savedAttempt);
        
        return convertAttemptToDTO(savedAttempt);
    }

    public List<QuizAttemptDTO> getStudentAttempts(Long studentId, Long quizId) {
        return quizAttemptRepository.findByStudentIdAndQuizId(studentId, quizId).stream()
                .map(this::convertAttemptToDTO)
                .collect(Collectors.toList());
    }

    private QuizAttemptDTO convertAttemptToDTO(QuizAttempt attempt) {
        QuizAttemptDTO dto = new QuizAttemptDTO();
        dto.setId(attempt.getId());
        dto.setQuizId(attempt.getQuiz().getId());
        dto.setQuizTitle(attempt.getQuiz().getTitle());
        dto.setStudentId(attempt.getStudent().getId());
        dto.setStudentName(attempt.getStudent().getName());
        dto.setStartedAt(attempt.getStartedAt());
        dto.setCompletedAt(attempt.getCompletedAt());
        dto.setScore(attempt.getScore());
        dto.setTotalPoints(attempt.getTotalPoints());
        dto.setPercentage(attempt.getPercentage());
        dto.setPassed(attempt.isPassed());
        dto.setStatus(attempt.getStatus());
        return dto;
    }
}
