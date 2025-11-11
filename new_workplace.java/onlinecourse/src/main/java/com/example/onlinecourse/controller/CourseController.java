package com.example.onlinecourse.controller;

import com.example.onlinecourse.dto.CourseDto;
import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.UserRepository;
import com.example.onlinecourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired private CourseService courseService;
    @Autowired private UserRepository userRepository;

    // Public: list published courses
    @GetMapping
    public ResponseEntity<List<CourseDto.Response>> listPublished() {
        return ResponseEntity.ok(courseService.listPublished());
    }

    // Public: view a course
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto.Response> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    // Create a course — only INSTRUCTOR or ADMIN
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR','ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody CourseDto.CreateRequest req, Principal principal) {
        String email = principal.getName();
        try {
            CourseDto.Response res = courseService.createCourse(email, req);
            return ResponseEntity.status(201).body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update course — INSTRUCTOR who owns it or ADMIN (controller-level doesn't check ownership; service could be extended)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR','ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CourseDto.UpdateRequest req, Principal principal) {
        try {
            CourseDto.Response res = courseService.updateCourse(id, principal.getName(), req);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Delete course — only ADMIN or instructor (we allow both here)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR','ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // List courses for the authenticated instructor (optional endpoint)
    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('ROLE_INSTRUCTOR','ROLE_ADMIN')")
    public ResponseEntity<?> myCourses(Principal principal) {
        User u = userRepository.findByEmail(principal.getName()).orElse(null);
        if (u == null) return ResponseEntity.status(404).body("User not found");
        return ResponseEntity.ok(courseService.listByInstructorId(u.getId()));
    }
}
