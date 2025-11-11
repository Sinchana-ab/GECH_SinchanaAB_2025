package com.coursemanagement.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coursemanagement.dto.ApiResponse;
import com.coursemanagement.dto.CertificateDTO;
import com.coursemanagement.service.CertificateService;
import com.coursemanagement.util.PdfCertificateGenerator;
import com.coursemanagement.model.Certificate;
import com.coursemanagement.repository.CertificateRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateRepository certificateRepository;

    public CertificateController(CertificateService certificateService,
                                CertificateRepository certificateRepository) {
        this.certificateService = certificateService;
        this.certificateRepository = certificateRepository;
    }

    @PostMapping("/student/certificates/generate")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> generateCertificate(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.valueOf(request.get("studentId").toString());
            Long courseId = Long.valueOf(request.get("courseId").toString());
            Double finalScore = Double.valueOf(request.get("finalScore").toString());

            CertificateDTO certificate = certificateService.generateCertificate(studentId, courseId, finalScore);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Certificate generated successfully", certificate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/student/certificates")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse> getStudentCertificates(@RequestParam Long studentId) {
        try {
            List<CertificateDTO> certificates = certificateService.getStudentCertificates(studentId);
            return ResponseEntity.ok(new ApiResponse(true, "Certificates retrieved successfully", certificates));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/public/certificates/verify/{certificateNumber}")
    public ResponseEntity<ApiResponse> verifyCertificate(@PathVariable String certificateNumber) {
        try {
            CertificateDTO certificate = certificateService.getCertificateByNumber(certificateNumber);
            return ResponseEntity.ok(new ApiResponse(true, "Certificate verified", certificate));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Certificate not found", null));
        }
    }

    /**
     * FIXED: Download certificate as PDF with proper headers and error handling
     */
    @GetMapping("/student/certificates/download/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        try {
            // Get certificate from database
            Certificate certificate = certificateRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));
            
            // Generate PDF bytes using iText
            byte[] pdfBytes = PdfCertificateGenerator.generateCertificate(certificate);
            
            // Set proper headers for PDF download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                    .filename("certificate-" + certificate.getCertificateNumber() + ".pdf")
                    .build()
            );
            headers.setContentLength(pdfBytes.length);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating certificate: " + e.getMessage()).getBytes());
        }
    }

    /**
     * NEW: Preview certificate as PDF in browser
     */
    @GetMapping("/student/certificates/preview/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<byte[]> previewCertificate(@PathVariable Long id) {
        try {
            Certificate certificate = certificateRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));
            
            byte[] pdfBytes = PdfCertificateGenerator.generateCertificate(certificate);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("inline")  // inline for preview
                    .filename("certificate-preview.pdf")
                    .build()
            );
            headers.setContentLength(pdfBytes.length);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/admin/certificates")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllCertificates() {
        try {
            List<CertificateDTO> certificates = certificateService.getAllCertificates();
            return ResponseEntity.ok(new ApiResponse(true, "All certificates retrieved", certificates));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/admin/certificates/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCertificate(@PathVariable Long id) {
        try {
            certificateService.deleteCertificate(id);
            return ResponseEntity.ok(new ApiResponse(true, "Certificate deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}