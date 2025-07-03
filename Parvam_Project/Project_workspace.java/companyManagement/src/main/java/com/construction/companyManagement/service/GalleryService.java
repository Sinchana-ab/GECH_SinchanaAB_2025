package com.construction.companyManagement.service;

import java.io.IOException;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.construction.companyManagement.dto.GalleryDTO;
import com.construction.companyManagement.model.Gallery;
import com.construction.companyManagement.repository.GalleryRepository;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepo;

    private final String UPLOAD_DIR = "src/main/resources/static/gallery-images/";

    public void saveImage(GalleryDTO dto) throws IOException {
        Gallery gallery;

        if (dto.getId() != null) {
            gallery = galleryRepo.findById(dto.getId()).orElse(new Gallery());
        } else {
            gallery = new Gallery();
            gallery.setUploadedAt(LocalDate.now());
        }

        gallery.setTitle(dto.getTitle());

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(dto.getImage().getOriginalFilename());
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
            Files.copy(dto.getImage().getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            gallery.setImagePath(filename);
        }

        galleryRepo.save(gallery);
    }

    public List<GalleryDTO> getAllImages() {
        return galleryRepo.findAll().stream().map(img -> {
            GalleryDTO dto = new GalleryDTO();
            dto.setId(img.getId());
            dto.setTitle(img.getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    public GalleryDTO getImageById(Long id) {
        return galleryRepo.findById(id).map(img -> {
            GalleryDTO dto = new GalleryDTO();
            dto.setId(img.getId());
            dto.setTitle(img.getTitle());
            return dto;
        }).orElseThrow(() -> new RuntimeException("Gallery item not found"));
    }

    public void deleteImage(Long id) {
        galleryRepo.deleteById(id);
    }
}
