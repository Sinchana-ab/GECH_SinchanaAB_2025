package com.construction.companyManagement.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construction.companyManagement.dto.TestimonialDTO;
import com.construction.companyManagement.model.Testimonial;
import com.construction.companyManagement.repository.TestimonialRepository;

@Service
public class TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepo;

    public void saveTestimonial(TestimonialDTO dto) throws IOException {
        Testimonial t = new Testimonial();
        t.setClientName(dto.getClientName());
        t.setFeedback(dto.getFeedback());
        t.setRating(dto.getRating());
        t.setDate(LocalDate.now());

        String fileName = dto.getImage().getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/testimonial-images/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Files.copy(dto.getImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        t.setImagePath(fileName);
        testimonialRepo.save(t);
    }

    public List<Testimonial> getAllTestimonials() {
        return testimonialRepo.findAll();
    }
}

