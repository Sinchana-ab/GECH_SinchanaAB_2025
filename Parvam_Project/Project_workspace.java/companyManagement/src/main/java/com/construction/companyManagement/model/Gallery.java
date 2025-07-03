package com.construction.companyManagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imagePath;

    private LocalDate uploadedAt;

    // Constructors
    public Gallery() {}

    public Gallery(Long id, String title, String imagePath, LocalDate uploadedAt) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.uploadedAt = uploadedAt;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public LocalDate getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(LocalDate uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

    
}
