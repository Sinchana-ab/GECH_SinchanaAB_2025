package com.coursemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.CourseDTO;
import com.coursemanagement.dto.UserDTO;
import com.coursemanagement.service.CourseService;
import com.coursemanagement.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;

    public AdminController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    // ========================================
    // USER MANAGEMENT
    // ========================================
    
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse(true, "Users retrieved successfully", users));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve users: " + e.getMessage(), null));
        }
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<ApiResponse> getUsersByRole(@PathVariable String role) {
        try {
            List<UserDTO> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(new ApiResponse(true, "Users retrieved successfully", users));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve users: " + e.getMessage(), null));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse(true, "User retrieved successfully", user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "User not found: " + e.getMessage(), null));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(new ApiResponse(true, "User updated successfully", updatedUser));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to update user: " + e.getMessage(), null));
        }
    }

    /**
     * ✅ IMPROVED: Delete user with better error handling
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Admin attempting to delete user with ID: " + id);
            
            // First check if user exists
            UserDTO user = userService.getUserById(id);
            System.out.println("✅ Found user: " + user.getName() + " (" + user.getRole() + ")");
            
            // Attempt deletion
            userService.deleteUser(id);
            
            System.out.println("✅ User deleted successfully");
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully", null));
            
        } catch (RuntimeException e) {
            // Expected business logic errors (e.g., instructor has courses)
            e.printStackTrace();
            System.err.println("❌ Business logic error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
                    
        } catch (Exception e) {
            // Unexpected errors
            e.printStackTrace();
            System.err.println("❌ Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete user: " + e.getMessage(), null));
        }
    }

    // ========================================
    // COURSE MANAGEMENT (Admin has full access)
    // ========================================
    
    @GetMapping("/courses")
    public ResponseEntity<ApiResponse> getAllCourses() {
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            return ResponseEntity.ok(new ApiResponse(true, "All courses retrieved successfully", courses));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve courses: " + e.getMessage(), null));
        }
    }

    @PutMapping("/courses/{id}/publish")
    public ResponseEntity<ApiResponse> publishCourse(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        try {
            CourseDTO course = courseService.getCourseById(id);
            course.setPublished(request.get("published"));
            CourseDTO updatedCourse = courseService.updateCourse(id, course);
            return ResponseEntity.ok(new ApiResponse(true, "Course publish status updated", updatedCourse));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to update course: " + e.getMessage(), null));
        }
    }

    /**
     * ✅ Admin delete course with proper error handling
     */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long id) {
        try {
            System.out.println("🗑️ Admin attempting to delete course with ID: " + id);
            courseService.deleteCourse(id);
            System.out.println("✅ Course deleted successfully");
            return ResponseEntity.ok(new ApiResponse(true, "Course and all related materials deleted successfully", null));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to delete course: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Failed to delete course: " + e.getMessage(), null));
        }
    }

    // ========================================
    // SYSTEM STATISTICS
    // ========================================
    
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getSystemStats() {
        try {
            // You can implement system-wide statistics here
            return ResponseEntity.ok(new ApiResponse(true, "Stats retrieved", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve stats: " + e.getMessage(), null));
        }
    }
}