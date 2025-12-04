package com.coursemanagement.controller;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.model.CourseMaterial;
import com.coursemanagement.repository.CourseMaterialRepository;
import com.coursemanagement.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DEBUG CONTROLLER - Remove this in production!
 * This helps diagnose file storage issues
 */
@RestController
@RequestMapping("/api/debug")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class DebugController {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    private final CourseMaterialRepository materialRepository;
    private final FileStorageService fileStorageService;

    public DebugController(CourseMaterialRepository materialRepository,
                          FileStorageService fileStorageService) {
        this.materialRepository = materialRepository;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Check if uploads directory exists and list files
     */
    @GetMapping("/files")
    public ResponseEntity<ApiResponse> listFiles() {
        try {
            Map<String, Object> info = new HashMap<>();
            
            // Upload directory info
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            File uploadFolder = uploadPath.toFile();
            
            info.put("uploadDirectory", uploadPath.toString());
            info.put("directoryExists", uploadFolder.exists());
            info.put("isDirectory", uploadFolder.isDirectory());
            info.put("canRead", uploadFolder.canRead());
            info.put("canWrite", uploadFolder.canWrite());
            
            // List all files recursively
            if (uploadFolder.exists() && uploadFolder.isDirectory()) {
                List<String> files = Files.walk(uploadPath)
                    .filter(Files::isRegularFile)
                    .map(p -> uploadPath.relativize(p).toString())
                    .collect(Collectors.toList());
                
                info.put("totalFiles", files.size());
                info.put("files", files);
            }
            
            // Get all materials from database
            List<CourseMaterial> materials = materialRepository.findAll();
            List<Map<String, Object>> materialInfo = materials.stream()
                .map(m -> {
                    Map<String, Object> mInfo = new HashMap<>();
                    mInfo.put("id", m.getId());
                    mInfo.put("title", m.getTitle());
                    mInfo.put("type", m.getMaterialType());
                    mInfo.put("filePath", m.getFilePath());
                    mInfo.put("fileName", m.getFileName());
                    
                    // Check if file actually exists
                    if (!"LINK".equals(m.getMaterialType())) {
                        try {
                            Resource resource = fileStorageService.loadFileAsResource(m.getFilePath());
                            mInfo.put("fileExists", resource.exists());
                            mInfo.put("fileSize", resource.contentLength());
                        } catch (Exception e) {
                            mInfo.put("fileExists", false);
                            mInfo.put("error", e.getMessage());
                        }
                    }
                    return mInfo;
                })
                .collect(Collectors.toList());
            
            info.put("materialsInDatabase", materials.size());
            info.put("materials", materialInfo);
            
            return ResponseEntity.ok(new ApiResponse(true, "Debug info retrieved", info));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    /**
     * Test file download for a specific material
     */
    @GetMapping("/test-download/{materialId}")
    public ResponseEntity<ApiResponse> testDownload(@PathVariable Long materialId) {
        try {
            CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));
            
            Map<String, Object> info = new HashMap<>();
            info.put("materialId", material.getId());
            info.put("title", material.getTitle());
            info.put("filePath", material.getFilePath());
            info.put("fileName", material.getFileName());
            info.put("type", material.getMaterialType());
            
            if (!"LINK".equals(material.getMaterialType())) {
                try {
                    Resource resource = fileStorageService.loadFileAsResource(material.getFilePath());
                    info.put("fileExists", resource.exists());
                    info.put("isReadable", resource.isReadable());
                    info.put("fileSize", resource.contentLength());
                    info.put("absolutePath", resource.getFile().getAbsolutePath());
                } catch (Exception e) {
                    info.put("error", e.getMessage());
                }
            }
            
            return ResponseEntity.ok(new ApiResponse(true, "Material info", info));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    /**
     * Check if a specific file path exists
     */
    @GetMapping("/check-file")
    public ResponseEntity<ApiResponse> checkFile(@RequestParam String filePath) {
        try {
            Map<String, Object> info = new HashMap<>();
            info.put("requestedPath", filePath);
            
            Path fullPath = Paths.get(uploadDir).resolve(filePath).normalize();
            File file = fullPath.toFile();
            
            info.put("absolutePath", fullPath.toString());
            info.put("exists", file.exists());
            info.put("isFile", file.isFile());
            info.put("canRead", file.canRead());
            info.put("size", file.length());
            
            return ResponseEntity.ok(new ApiResponse(true, "File check complete", info));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }
}