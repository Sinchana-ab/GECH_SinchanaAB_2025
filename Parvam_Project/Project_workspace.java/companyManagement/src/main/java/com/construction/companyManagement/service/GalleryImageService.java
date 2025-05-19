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

import com.construction.companyManagement.dto.GalleryImageDTO;
import com.construction.companyManagement.model.GalleryImage;
import com.construction.companyManagement.repository.GalleryImageRepository;

@Service
public class GalleryImageService {

    @Autowired
    private GalleryImageRepository galleryImageRepository;

    public void saveGalleryImage(GalleryImageDTO dto) throws IOException {
        GalleryImage galleryImage = new GalleryImage();
        galleryImage.setTitle(dto.getTitle());
        galleryImage.setUploadedAt(LocalDate.now());

        String fileName = dto.getImage().getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/gallery-images/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Files.copy(dto.getImage().getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        galleryImage.setImagePath(fileName);
        galleryImageRepository.save(galleryImage);
    }

    public List<GalleryImage> getAllImages() {
        return galleryImageRepository.findAll();
    }
}
