package com.coursemanagement.controller;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.RatingDTO;
import com.coursemanagement.model.RatingStatistics;
import com.coursemanagement.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/student/ratings")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> addRating(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.valueOf(request.get("studentId").toString());
            Long courseId = Long.valueOf(request.get("courseId").toString());
            Integer rating = Integer.valueOf(request.get("rating").toString());
            String review = request.get("review") != null ? request.get("review").toString() : "";

            RatingDTO ratingDTO = ratingService.addOrUpdateRating(studentId, courseId, rating, review);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Rating submitted successfully", ratingDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/public/courses/{courseId}/ratings")
    public ResponseEntity<ApiResponse> getCourseRatings(@PathVariable Long courseId) {
        try {
            List<RatingDTO> ratings = ratingService.getCourseRatings(courseId);
            return ResponseEntity.ok(new ApiResponse(true, "Ratings retrieved successfully", ratings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/public/courses/{courseId}/ratings/top")
    public ResponseEntity<ApiResponse> getTopRatings(@PathVariable Long courseId) {
        try {
            List<RatingDTO> ratings = ratingService.getTopRatings(courseId);
            return ResponseEntity.ok(new ApiResponse(true, "Top ratings retrieved", ratings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/public/courses/{courseId}/ratings/statistics")
    public ResponseEntity<ApiResponse> getRatingStatistics(@PathVariable Long courseId) {
        try {
            RatingStatistics stats = ratingService.getRatingStatistics(courseId);
            return ResponseEntity.ok(new ApiResponse(true, "Statistics retrieved", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/student/ratings")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getStudentRatings(@RequestParam Long studentId) {
        try {
            List<RatingDTO> ratings = ratingService.getStudentRatings(studentId);
            return ResponseEntity.ok(new ApiResponse(true, "Student ratings retrieved", ratings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/student/ratings/{id}/helpful")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> markAsHelpful(@PathVariable Long id) {
        try {
            RatingDTO rating = ratingService.markAsHelpful(id);
            return ResponseEntity.ok(new ApiResponse(true, "Marked as helpful", rating));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/student/ratings/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<ApiResponse> deleteRating(@PathVariable Long id) {
        try {
            ratingService.deleteRating(id);
            return ResponseEntity.ok(new ApiResponse(true, "Rating deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}