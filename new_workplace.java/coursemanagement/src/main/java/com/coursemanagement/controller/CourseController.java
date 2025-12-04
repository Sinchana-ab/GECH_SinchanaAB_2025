package com.coursemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.CourseDTO;
import com.coursemanagement.service.CourseService;
import com.coursemanagement.service.CustomUserDetails;
import com.coursemanagement.model.Course;
import com.coursemanagement.repository.CourseRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class CourseController {

    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    // ========================================
    // PUBLIC ENDPOINTS (No Authentication)
    // ========================================
    
    @GetMapping("/public/courses")
    public ResponseEntity<ApiResponse> getPublishedCourses() {
        try {
            List<CourseDTO> courses = courseService.getPublishedCourses();
            return ResponseEntity.ok(new ApiResponse(true, "Courses retrieved successfully", courses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/public/courses/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long id) {
        try {
            CourseDTO course = courseService.getCourseById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Course retrieved successfully", course));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/public/courses/search")
    public ResponseEntity<ApiResponse> searchCourses(@RequestParam String keyword) {
        try {
            List<CourseDTO> courses = courseService.searchCourses(keyword);
            return ResponseEntity.ok(new ApiResponse(true, "Search results", courses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    // ========================================
    // INSTRUCTOR ENDPOINTS
    // ========================================
    
    @PostMapping("/instructor/courses")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseDTO courseDTO) {
        try {
            CourseDTO createdCourse = courseService.createCourse(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Course created successfully", createdCourse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/instructor/courses")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getInstructorCourses(@RequestParam Long instructorId) {
        try {
            List<CourseDTO> courses = courseService.getCoursesByInstructor(instructorId);
            return ResponseEntity.ok(new ApiResponse(true, "Courses retrieved successfully", courses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/instructor/courses/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> updateCourse(
            @PathVariable Long id, 
            @RequestBody CourseDTO courseDTO,
            Authentication authentication) {
        try {
            // Authorization check
            if (!canAccessCourse(id, authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You don't have permission to update this course", null));
            }

            CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            return ResponseEntity.ok(new ApiResponse(true, "Course updated successfully", updatedCourse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/instructor/courses/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> deleteCourse(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            // Authorization check - instructors can only delete their own courses
            if (!canAccessCourse(id, authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You don't have permission to delete this course", null));
            }

            courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse(true, "Course deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to delete course: " + e.getMessage(), null));
        }
    }

    // ========================================
    // HELPER METHODS
    // ========================================
    
    /**
     * Check if user can access course
     * - Admin can access all courses
     * - Instructor can only access their own courses
     */
    private boolean canAccessCourse(Long courseId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        // Admin has full access
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        
        // Check if instructor owns the course
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return false;
        }
        
        return course.getInstructor().getId().equals(userDetails.getUserId());
    }
}