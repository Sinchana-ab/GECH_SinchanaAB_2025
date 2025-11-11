//package com.coursemanagement.controller;
//
//import com.coursemanagement.dto.ProgressDTO;
//import com.coursemanagement.service.ProgressService;
//import com.coursemanagement.service.progressService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/progress")
//@CrossOrigin(origins = "*")
//public class ProgressController {
//    
//    @Autowired
//    private ProgressService progressService;
//    
//    /**
//     * Get detailed progress for a student in a specific course
//     */
//    @GetMapping("/student/{studentId}/course/{courseId}")
//    public ResponseEntity<Map<String, Object>> getStudentCourseProgress(
//            @PathVariable Long studentId,
//            @PathVariable Long courseId) {
//        
//        Map<String, Object> response = new HashMap<>();
//        
//        try {
//            ProgressDTO progress = progressService.getStudentCourseProgress(studentId, courseId);
//            response.put("success", true);
//            response.put("data", progress);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            response.put("success", false);
//            response.put("message", "Failed to fetch progress: " + e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//    
//    /**
//     * Mark a material as viewed
//     */
//    @PostMapping("/material/{materialId}/view")
//    public ResponseEntity<Map<String, Object>> markMaterialAsViewed(
//            @PathVariable Long materialId,
//            @RequestParam Long studentId) {
//        
//        Map<String, Object> response = new HashMap<>();
////        
////        try {
////            progressService.markMaterialAsViewed(studentId, materialId);
////            response.put("success", true);
////            response.put("message", "Material marked as viewed");
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            response.put("success", false);
////            response.put("message", "Failed to update progress: " + e.getMessage());
////            return ResponseEntity.badRequest().body(response);
////        }
//    }
//    
//    /**
//     * Get all viewed materials for a student in a course
//     */
//   // @GetMapping("/student/{studentId}/course/{courseId}/materials")
////    public ResponseEntity<Map<String, Object>> getViewedMaterials(
////            @PathVariable Long studentId,
////            @PathVariable Long courseId) {
////        
////        Map<String, Object> response = new HashMap<>();
////        
////        try {
////            var viewedMaterials = progressService.getViewedMaterials(studentId, courseId);
////            response.put("success", true);
////            response.put("data", viewedMaterials);
////            return ResponseEntity.ok(response);
////        } catch (Exception e) {
////            response.put("success", false);
////            response.put("message", "Failed to fetch viewed materials: " + e.getMessage());
////            return ResponseEntity.badRequest().body(response);
////        }
////    }
//}