package com.coursemanagement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coursemanagement.dto.CourseDTO;
import com.coursemanagement.model.Course;
import com.coursemanagement.model.User;
import com.coursemanagement.repository.CourseRepository;
import com.coursemanagement.repository.EnrollmentRepository;
import com.coursemanagement.repository.UserRepository;
import com.coursemanagement.repository.CourseMaterialRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseMaterialRepository materialRepository;
    private final FileStorageService fileStorageService;

    public CourseService(CourseRepository courseRepository, 
                        UserRepository userRepository,
                        EnrollmentRepository enrollmentRepository,
                        CourseMaterialRepository materialRepository,
                        FileStorageService fileStorageService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.materialRepository = materialRepository;
        this.fileStorageService = fileStorageService;
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        User instructor = userRepository.findById(courseDTO.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setInstructor(instructor);
        course.setCategory(courseDTO.getCategory());
        course.setLevel(courseDTO.getLevel());
        course.setThumbnail(courseDTO.getThumbnail());
        course.setDurationHours(courseDTO.getDurationHours());
        course.setPrice(courseDTO.getPrice());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        course.setPublished(courseDTO.isPublished());

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return convertToDTO(course);
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getPublishedCourses() {
        return courseRepository.findByPublishedTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> searchCourses(String keyword) {
        return courseRepository.searchCourses(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setCategory(courseDTO.getCategory());
        course.setLevel(courseDTO.getLevel());
        course.setThumbnail(courseDTO.getThumbnail());
        course.setDurationHours(courseDTO.getDurationHours());
        course.setPrice(courseDTO.getPrice());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        course.setPublished(courseDTO.isPublished());

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    /**
     * ✅ IMPROVED: Delete course with proper material cleanup
     */
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        try {
            // Step 1: Delete all physical files for this course's materials
            course.getMaterials().forEach(material -> {
                if (!"LINK".equals(material.getMaterialType())) {
                    try {
                        fileStorageService.deleteFile(material.getFilePath());
                    } catch (Exception e) {
                        System.err.println("Failed to delete file: " + material.getFilePath() + " - " + e.getMessage());
                    }
                }
            });
            
            // Step 2: Delete the course (cascade will handle materials, enrollments, etc.)
            courseRepository.delete(course);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete course: " + e.getMessage(), e);
        }
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setInstructorId(course.getInstructor().getId());
        dto.setInstructorName(course.getInstructor().getName());
        dto.setCategory(course.getCategory());
        dto.setLevel(course.getLevel());
        dto.setThumbnail(course.getThumbnail());
        dto.setDurationHours(course.getDurationHours());
        dto.setPrice(course.getPrice());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        dto.setPublished(course.isPublished());
        dto.setEnrollmentCount(enrollmentRepository.countByCourseId(course.getId()));
        return dto;
    }
}