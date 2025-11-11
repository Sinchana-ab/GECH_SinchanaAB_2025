package com.coursemanagement.controller;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.CourseMaterialDTO;
import com.coursemanagement.service.CourseMaterialService;
import com.coursemanagement.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public FileUploadController(CourseMaterialService materialService, 
                               FileStorageService fileStorageService) {
        this.materialService = materialService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Upload course material (video, PDF, document, image)
     */
    @PostMapping("/instructor/courses/{courseId}/materials/upload")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> uploadMaterial(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("materialType") String materialType) {
        
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Please select a file to upload", null));
            }

            // Validate file size (max 100MB)
            if (file.getSize() > 100 * 1024 * 1024) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "File size exceeds 100MB limit", null));
            }

            CourseMaterialDTO material = materialService.uploadMaterial(
                    courseId, title, description, materialType, file);

            return ResponseEntity.ok(new ApiResponse(true, "File uploaded successfully", material));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to upload file: " + e.getMessage(), null));
        }
    }

    /**
     * Add external link (YouTube, etc.)
     */
    @PostMapping("/instructor/courses/{courseId}/materials/link")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> addExternalLink(
            @PathVariable Long courseId,
            @RequestBody CourseMaterialDTO materialDTO) {
        
        try {
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
//    @GetMapping("/courses/{courseId}/materials")
//    public ResponseEntity<ApiResponse> getCourseMaterials(@PathVariable Long courseId) {
//        try {
//            List<CourseMaterialDTO> materials = materialService.getCourseMaterials(courseId);
//            return ResponseEntity.ok(new ApiResponse(true, "Materials retrieved successfully", materials));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse(false, e.getMessage(), null));
//        }
//    }
    
    /**
     * Get material by ID (for viewing)
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
     * Download/Stream file
     */
    @GetMapping("/courses/materials/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                contentType = "application/octet-stream";
            }

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update material
     */
    @PutMapping("/instructor/materials/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> updateMaterial(
            @PathVariable Long id,
            @RequestBody CourseMaterialDTO materialDTO) {
        
        try {
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
    public ResponseEntity<ApiResponse> deleteMaterial(@PathVariable Long id) {
        try {
            materialService.deleteMaterial(id);
            return ResponseEntity.ok(new ApiResponse(true, "Material deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}