package com.example.onlinecourse.controller;

import com.example.onlinecourse.dto.CourseDto;
import com.example.onlinecourse.model.User;
import com.example.onlinecourse.model.Role;
import com.example.onlinecourse.repository.UserRepository;
import com.example.onlinecourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CourseService courseService;

    // ---------- INSTRUCTOR CRUD ----------

    @PostMapping("/instructors")
    public ResponseEntity<?> addInstructor(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Instructor already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_INSTRUCTOR);
        userRepository.save(user);
        return ResponseEntity.ok("Instructor added successfully");
    }

    @GetMapping("/instructors")
    public List<User> getAllInstructors() {
        return userRepository.findByRole(Role.ROLE_INSTRUCTOR);
    }

    @PutMapping("/instructors/{id}")
    public ResponseEntity<?> updateInstructor(@PathVariable Long id, @RequestBody User updated) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEmail(updated.getEmail());
                    userRepository.save(existing);
                    return ResponseEntity.ok("Instructor updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("Instructor deleted successfully");
    }

    // ---------- COURSE CRUD ----------

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto.CreateRequest req) {
        if (req.instructorEmail == null || req.instructorEmail.isBlank()) {
            return ResponseEntity.badRequest().body("instructorEmail required");
        }
        return ResponseEntity.ok(courseService.createCourse(req.instructorEmail, req));
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.listAll());
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseDto.UpdateRequest req) {
        return ResponseEntity.ok(courseService.updateCourseAdmin(id, req));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
