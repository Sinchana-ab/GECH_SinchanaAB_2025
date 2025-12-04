package com.coursemanagement.controller;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.CourseMaterialDTO;
import com.coursemanagement.model.Course;
import com.coursemanagement.service.CourseMaterialService;
import com.coursemanagement.service.FileStorageService;
import com.coursemanagement.service.CustomUserDetails;
import com.coursemanagement.repository.CourseRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class FileUploadController {

    private final CourseMaterialService materialService;
    private final FileStorageService fileStorageService;
    private final CourseRepository courseRepository;

    public FileUploadController(CourseMaterialService materialService, 
                               FileStorageService fileStorageService,
                               CourseRepository courseRepository) {
        this.materialService = materialService;
        this.fileStorageService = fileStorageService;
        this.courseRepository = courseRepository;
    }

    /**
     * Upload course material
     */
    @PostMapping("/instructor/courses/{courseId}/materials/upload")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> uploadMaterial(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("materialType") String materialType,
            Authentication authentication) {
        
        try {
            if (!canAccessCourse(courseId, authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You don't have permission to add materials to this course", null));
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Please select a file to upload", null));
            }

            if (file.getSize() > 100 * 1024 * 1024) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "File size exceeds 100MB limit", null));
            }

            CourseMaterialDTO material = materialService.uploadMaterial(
                    courseId, title, description, materialType, file);

            return ResponseEntity.ok(new ApiResponse(true, "File uploaded successfully", material));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to upload file: " + e.getMessage(), null));
        }
    }

    /**
     * Add external link
     */
    @PostMapping("/instructor/courses/{courseId}/materials/link")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> addExternalLink(
            @PathVariable Long courseId,
            @RequestBody CourseMaterialDTO materialDTO,
            Authentication authentication) {
        
        try {
            if (!canAccessCourse(courseId, authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You don't have permission to add materials to this course", null));
            }

            CourseMaterialDTO material = materialService.addExternalLink(
                    courseId,
                    materialDTO.getTitle(),
                    materialDTO.getDescription(),
                    materialDTO.getMaterialType(),
                    materialDTO.getFilePath());

            return ResponseEntity.ok(new ApiResponse(true, "Link added successfully", material));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Get all materials for a course
     */
    @GetMapping("/courses/{courseId}/materials")
    public ResponseEntity<ApiResponse> getCourseMaterials(@PathVariable Long courseId) {
        try {
            List<CourseMaterialDTO> materials = materialService.getCourseMaterials(courseId);
            return ResponseEntity.ok(new ApiResponse(true, "Materials retrieved successfully", materials));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
   
    /**
     * Get material by ID
     */
    @GetMapping("/materials/{id}")
    public ResponseEntity<ApiResponse> getMaterialById(@PathVariable Long id) {
        try {
            CourseMaterialDTO material = materialService.getMaterialById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Material retrieved", material));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * ✅ FIXED: Download/Stream file - NOW WORKS WITH FULL PATH
     */
    @GetMapping("/courses/materials/download/{filePath:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filePath, 
            HttpServletRequest request) {
        try {
            System.out.println("🔍 Download request for: " + filePath);
            
            // Load the file as a Resource
            Resource resource = fileStorageService.loadFileAsResource(filePath);
            
            if (!resource.exists() || !resource.isReadable()) {
                System.err.println("❌ File not found or not readable: " + filePath);
                return ResponseEntity.notFound().build();
            }

            // Determine content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                System.out.println("⚠️ Could not determine file type, using default");
            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            System.out.println("✅ Serving file: " + resource.getFilename() + " (" + contentType + ")");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                    .body(resource);
                    
        } catch (Exception e) {
            System.err.println("❌ Error serving file: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update material
     */
    @PutMapping("/instructor/materials/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> updateMaterial(
            @PathVariable Long id,
            @RequestBody CourseMaterialDTO materialDTO,
            Authentication authentication) {
        
        try {
            CourseMaterialDTO existingMaterial = materialService.getMaterialById(id);
            
            if (!canAccessCourse(existingMaterial.getCourseId(), authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You don't have permission to update this material", null));
            }

            CourseMaterialDTO updated = materialService.updateMaterial(
                    id,
                    materialDTO.getTitle(),
                    materialDTO.getDescription(),
                    materialDTO.getOrderIndex());

            return ResponseEntity.ok(new ApiResponse(true, "Material updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * Delete material
     */
    @DeleteMapping("/instructor/materials/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> deleteMaterial(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            CourseMaterialDTO existingMaterial = materialService.getMaterialById(id);
            
            if (!canAccessCourse(existingMaterial.getCourseId(), authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "You don't have permission to delete this material", null));
            }

            materialService.deleteMaterial(id);
            return ResponseEntity.ok(new ApiResponse(true, "Material deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    private boolean canAccessCourse(Long courseId, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }
        
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return false;
        }
        
        return course.getInstructor().getId().equals(userDetails.getUserId());
    }
}