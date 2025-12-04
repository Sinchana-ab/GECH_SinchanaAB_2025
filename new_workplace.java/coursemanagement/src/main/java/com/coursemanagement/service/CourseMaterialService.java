package com.coursemanagement.service;

import com.coursemanagement.dto.CourseMaterialDTO;
import com.coursemanagement.model.Course;
import com.coursemanagement.model.CourseMaterial;
import com.coursemanagement.repository.CourseMaterialRepository;
import com.coursemanagement.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseMaterialService {

    private final CourseMaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final FileStorageService fileStorageService;

    public CourseMaterialService(CourseMaterialRepository materialRepository,
                                CourseRepository courseRepository,
                                FileStorageService fileStorageService) {
        this.materialRepository = materialRepository;
        this.courseRepository = courseRepository;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Upload course material
     */
    public CourseMaterialDTO uploadMaterial(Long courseId, String title, String description,
                                           String materialType, MultipartFile file) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Determine folder based on material type
        String folder = "courses/" + courseId + "/" + materialType.toLowerCase();

        // Store file
        String filePath = fileStorageService.storeFile(file, folder);

        // Create material entity
        CourseMaterial material = new CourseMaterial();
        material.setCourse(course);
        material.setTitle(title);
        material.setDescription(description);
        material.setMaterialType(materialType);
        material.setFilePath(filePath);
        material.setFileName(file.getOriginalFilename());
        material.setFileSize(file.getSize());

        // Get next order index
        List<CourseMaterial> existingMaterials = materialRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        material.setOrderIndex(existingMaterials.size() + 1);

        CourseMaterial savedMaterial = materialRepository.save(material);
        return convertToDTO(savedMaterial);
    }

    /**
     * Add external link (YouTube, external resource)
     */
    public CourseMaterialDTO addExternalLink(Long courseId, String title, String description,
                                            String materialType, String url) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseMaterial material = new CourseMaterial();
        material.setCourse(course);
        material.setTitle(title);
        material.setDescription(description);
        material.setMaterialType(materialType);
        material.setFilePath(url);
        material.setFileName("External Link");

        List<CourseMaterial> existingMaterials = materialRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        material.setOrderIndex(existingMaterials.size() + 1);

        CourseMaterial savedMaterial = materialRepository.save(material);
        return convertToDTO(savedMaterial);
    }

    /**
     * Get all materials for a course
     */
    public List<CourseMaterialDTO> getCourseMaterials(Long courseId) {
        return materialRepository.findByCourseIdOrderByOrderIndexAsc(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get material by ID
     */
    public CourseMaterialDTO getMaterialById(Long id) {
        CourseMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        return convertToDTO(material);
    }

    /**
     * Update material
     */
    public CourseMaterialDTO updateMaterial(Long id, String title, String description, Integer orderIndex) {
        CourseMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        if (title != null && !title.isEmpty()) {
            material.setTitle(title);
        }
        if (description != null) {
            material.setDescription(description);
        }
        if (orderIndex != null) {
            material.setOrderIndex(orderIndex);
        }

        CourseMaterial updated = materialRepository.save(material);
        return convertToDTO(updated);
    }

    /**
     * Delete material - WITH FILE DELETION
     */
    public void deleteMaterial(Long id) {
        CourseMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found with id: " + id));

        // Delete physical file if not external link
        if (!"LINK".equals(material.getMaterialType())) {
            try {
                fileStorageService.deleteFile(material.getFilePath());
            } catch (Exception e) {
                // Log error but continue with database deletion
                System.err.println("Failed to delete file: " + material.getFilePath() + " - " + e.getMessage());
            }
        }

        // Delete from database
        materialRepository.deleteById(id);
    }

    /**
     * Delete all materials for a course
     */
    public void deleteCourseMaterials(Long courseId) {
        List<CourseMaterial> materials = materialRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        
        // Delete each material (will also delete files)
        for (CourseMaterial material : materials) {
            deleteMaterial(material.getId());
        }
    }

    private CourseMaterialDTO convertToDTO(CourseMaterial material) {
        CourseMaterialDTO dto = new CourseMaterialDTO();
        dto.setId(material.getId());
        dto.setCourseId(material.getCourse().getId());
        dto.setTitle(material.getTitle());
        dto.setDescription(material.getDescription());
        dto.setMaterialType(material.getMaterialType());
        dto.setFilePath(material.getFilePath());
        dto.setFileName(material.getFileName());
        dto.setFileSize(material.getFileSize());
        dto.setOrderIndex(material.getOrderIndex());
        dto.setCreatedAt(material.getCreatedAt());
        return dto;
    }
}