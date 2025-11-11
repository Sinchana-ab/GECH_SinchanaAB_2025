package com.example.onlinecourse.service;

import com.example.onlinecourse.dto.CourseDto;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;

    private CourseDto.Response toResponse(Course c) {
        return CourseDto.Response.fromEntity(c);
    }

    public CourseDto.Response createCourse(String instructorEmail, CourseDto.CreateRequest req) {
        User instructor = userRepository.findByEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found for email: " + instructorEmail));

        Course c = new Course();
        c.setTitle(req.title);
        c.setDescription(req.description);
        c.setPublished(req.published != null ? req.published : false);
        c.setInstructor(instructor);
        Course saved = courseRepository.save(c);
        return toResponse(saved);
    }

    public CourseDto.Response updateCourse(Long courseId, String requesterEmail, CourseDto.UpdateRequest req) {
        Course c = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (req.title != null) c.setTitle(req.title);
        if (req.description != null) c.setDescription(req.description);
        if (req.published != null) c.setPublished(req.published);
        Course saved = courseRepository.save(c);
        return toResponse(saved);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<CourseDto.Response> listPublished() {
        return courseRepository.findByPublishedTrue()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CourseDto.Response getById(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return toResponse(c);
    }

    public List<CourseDto.Response> listByInstructorId(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    // ---------- ADMIN helpers ----------

    // return ALL courses (admin)
    public List<CourseDto.Response> listAll() {
        return courseRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // admin-level update (no ownership check)
    public CourseDto.Response updateCourseAdmin(Long courseId, CourseDto.UpdateRequest req) {
        Course c = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (req.title != null) c.setTitle(req.title);
        if (req.description != null) c.setDescription(req.description);
        if (req.published != null) c.setPublished(req.published);
        Course saved = courseRepository.save(c);
        return toResponse(saved);
    }
}
