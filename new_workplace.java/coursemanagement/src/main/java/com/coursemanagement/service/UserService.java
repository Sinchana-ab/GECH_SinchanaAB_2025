package com.coursemanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coursemanagement.dto.UserDTO;
import com.coursemanagement.model.User;
import com.coursemanagement.repository.UserRepository;
import com.coursemanagement.repository.CourseRepository;
import com.coursemanagement.repository.EnrollmentRepository;
import com.coursemanagement.repository.ExamAttemptRepository;
import com.coursemanagement.repository.QuizAttemptRepository;
import com.coursemanagement.repository.RatingRepository;
import com.coursemanagement.repository.CertificateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final RatingRepository ratingRepository;
    private final CertificateRepository certificateRepository;
    private final ExamAttemptRepository examAttemptRepository;

    

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService,
			CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
			QuizAttemptRepository quizAttemptRepository, RatingRepository ratingRepository,
			CertificateRepository certificateRepository, ExamAttemptRepository examAttemptRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
		this.courseRepository = courseRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.quizAttemptRepository = quizAttemptRepository;
		this.ratingRepository = ratingRepository;
		this.certificateRepository = certificateRepository;
		this.examAttemptRepository = examAttemptRepository;
	}

	public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "ROLE_STUDENT");
        user.setPhone(userDTO.getPhone());
        user.setBio(userDTO.getBio());

        User savedUser = userRepository.save(user);
        
        // Send welcome email
        emailService.sendWelcomeEmail(savedUser);
        
        return convertToDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setBio(userDTO.getBio());
        
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    /**
     * ✅ FIXED: Delete user with proper cleanup of related entities
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        try {
            // Check user role and handle accordingly
            String role = user.getRole();
            
            if ("ROLE_INSTRUCTOR".equals(role) || "ROLE_ADMIN".equals(role)) {
                // Check if instructor has courses
                long courseCount = courseRepository.findByInstructorId(id).size();
                if (courseCount > 0) {
                    throw new RuntimeException("Cannot delete instructor who has created courses. " +
                            "Please delete or reassign their courses first.");
                }
            }
            
            if ("ROLE_STUDENT".equals(role) || "ROLE_ADMIN".equals(role)) {
                // Delete student's certificates first (foreign key constraint)
                certificateRepository.findByStudentId(id).forEach(cert -> {
                    certificateRepository.delete(cert);
                });
                
                // Delete student's ratings
                ratingRepository.findByStudentId(id).forEach(rating -> {
                    ratingRepository.delete(rating);
                });
                
                // Delete quiz attempts
                quizAttemptRepository.findByStudentIdAndStatus(id, "COMPLETED").forEach(attempt -> {
                    quizAttemptRepository.delete(attempt);
                });
                quizAttemptRepository.findByStudentIdAndStatus(id, "IN_PROGRESS").forEach(attempt -> {
                    quizAttemptRepository.delete(attempt);
                });
                
                // Delete enrollments (this should cascade delete material progress)
                enrollmentRepository.findByStudentId(id).forEach(enrollment -> {
                    enrollmentRepository.delete(enrollment);
                });
            }
            
            // Finally delete the user
            userRepository.delete(user);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setPhone(user.getPhone());
        dto.setBio(user.getBio());
        return dto;
    }
}