package com.coursemanagement.service;

import com.coursemanagement.dto.RatingDTO;
import com.coursemanagement.model.Course;
import com.coursemanagement.model.Enrollment;
import com.coursemanagement.model.Rating;
import com.coursemanagement.model.RatingStatistics;
import com.coursemanagement.model.User;
import com.coursemanagement.repository.CourseRepository;
import com.coursemanagement.repository.EnrollmentRepository;
import com.coursemanagement.repository.RatingRepository;
import com.coursemanagement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository,
                        CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Add or update rating for a course
     */
    public RatingDTO addOrUpdateRating(Long studentId, Long courseId, Integer rating, String review) {
        // Validate student
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Validate course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Check if student is enrolled
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("You must be enrolled in the course to rate it"));

        // Validate rating
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        // Check if rating already exists
        Rating ratingEntity = ratingRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElse(new Rating());

        ratingEntity.setStudent(student);
        ratingEntity.setCourse(course);
        ratingEntity.setRating(rating);
        ratingEntity.setReview(review);

        Rating savedRating = ratingRepository.save(ratingEntity);
        return convertToDTO(savedRating);
    }

    /**
     * Get all ratings for a course
     */
    public List<RatingDTO> getCourseRatings(Long courseId) {
        return ratingRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get top/helpful ratings for a course
     */
    public List<RatingDTO> getTopRatings(Long courseId) {
        return ratingRepository.findTopRatedByCourseId(courseId).stream()
                .limit(10)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get ratings by student
     */
    public List<RatingDTO> getStudentRatings(Long studentId) {
        return ratingRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get average rating for a course
     */
    public Double getAverageRating(Long courseId) {
        Double avg = ratingRepository.getAverageRatingByCourseId(courseId);
        return avg != null ? avg : 0.0;
    }

    /**
     * Get rating count for a course
     */
    public Long getRatingCount(Long courseId) {
        return ratingRepository.countByCourseId(courseId);
    }

    /**
     * Mark rating as helpful
     */
    public RatingDTO markAsHelpful(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        
        rating.setHelpfulCount(rating.getHelpfulCount() + 1);
        rating.setHelpful(true);
        
        Rating updated = ratingRepository.save(rating);
        return convertToDTO(updated);
    }

    /**
     * Delete rating
     */
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    /**
     * Get rating statistics for a course
     */
    public RatingStatistics getRatingStatistics(Long courseId) {
        List<Rating> ratings = ratingRepository.findByCourseId(courseId);
        
        if (ratings.isEmpty()) {
            return new RatingStatistics(0.0, 0L, 0, 0, 0, 0, 0);
        }

        Double average = getAverageRating(courseId);
        Long totalCount = (long) ratings.size();

        int fiveStars = (int) ratings.stream().filter(r -> r.getRating() == 5).count();
        int fourStars = (int) ratings.stream().filter(r -> r.getRating() == 4).count();
        int threeStars = (int) ratings.stream().filter(r -> r.getRating() == 3).count();
        int twoStars = (int) ratings.stream().filter(r -> r.getRating() == 2).count();
        int oneStar = (int) ratings.stream().filter(r -> r.getRating() == 1).count();

        return new RatingStatistics(average, totalCount, fiveStars, fourStars, 
                                   threeStars, twoStars, oneStar);
    }

    private RatingDTO convertToDTO(Rating rating) {
        RatingDTO dto = new RatingDTO();
        dto.setId(rating.getId());
        dto.setStudentId(rating.getStudent().getId());
        dto.setStudentName(rating.getStudent().getName());
        dto.setCourseId(rating.getCourse().getId());
        dto.setCourseTitle(rating.getCourse().getTitle());
        dto.setRating(rating.getRating());
        dto.setReview(rating.getReview());
        dto.setCreatedAt(rating.getCreatedAt());
        dto.setUpdatedAt(rating.getUpdatedAt());
        dto.setHelpfulCount(rating.getHelpfulCount());
        return dto;
    }
}