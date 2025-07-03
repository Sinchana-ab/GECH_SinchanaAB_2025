package com.construction.companyManagement.dto;

import org.springframework.web.multipart.MultipartFile;

public class GalleryDTO {
    private Long id;
    private String title;
    private MultipartFile image;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}
