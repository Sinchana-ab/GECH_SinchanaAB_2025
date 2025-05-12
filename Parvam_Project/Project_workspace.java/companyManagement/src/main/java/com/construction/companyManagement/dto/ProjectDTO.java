package com.construction.companyManagement.dto;


import org.springframework.web.multipart.MultipartFile;

public class ProjectDTO {
    private String title;
    private String location;
    private String description;
    private MultipartFile image; // Image file

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}
