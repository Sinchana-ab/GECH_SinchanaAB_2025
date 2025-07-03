package com.construction.companyManagement.service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.construction.companyManagement.dto.TestimonialDTO;
import com.construction.companyManagement.model.Testimonial;
import com.construction.companyManagement.repository.TestimonialRepository;

@Service
public class TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepo;

    private final String UPLOAD_DIR = "src/main/resources/static/testimonial-images/";

    public void saveTestimonial(TestimonialDTO dto) throws IOException {
        Testimonial testimonial;

        if (dto.getId() != null) {
            testimonial = testimonialRepo.findById(dto.getId())
                    .orElse(new Testimonial()); // fallback in case ID is invalid
        } else {
            testimonial = new Testimonial();
            testimonial.setDate(LocalDate.now()); // only set date on new entries
        }

        testimonial.setClientName(dto.getClientName());
        testimonial.setFeedback(dto.getFeedback());
        testimonial.setRating(dto.getRating());

        // Handle new image upload (optional on edit)
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String fileName = StringUtils.cleanPath(dto.getImage().getOriginalFilename());

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

            Files.copy(dto.getImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            testimonial.setImagePath(fileName);
        }

        testimonialRepo.save(testimonial);
    }

    public List<Testimonial> getAllTestimonials() {
        return testimonialRepo.findAll();
    }

    public TestimonialDTO getTestimonialById(Long id) {
        return testimonialRepo.findById(id).map(t -> {
            TestimonialDTO dto = new TestimonialDTO();
            dto.setId(t.getId());
            dto.setClientName(t.getClientName());
            dto.setFeedback(t.getFeedback());
            dto.setRating(t.getRating());
            // image is skipped for editing to avoid exposing file path
            return dto;
        }).orElseThrow(() -> new RuntimeException("Testimonial not found"));
    }

    public void deleteTestimonial(Long id) {
        testimonialRepo.deleteById(id);
    }
}
