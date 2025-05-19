package com.construction.companyManagement.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class GalleryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imagePath;
    private LocalDate uploadedAt;
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
	public GalleryImage(Long id, String title, String imagePath, LocalDate uploadedAt) {
		super();
		this.id = id;
		this.title = title;
		this.imagePath = imagePath;
		this.uploadedAt = uploadedAt;
	}
	public GalleryImage() {
		super();
	}
	@Override
	public String toString() {
		return "GalleryImage [id=" + id + ", title=" + title + ", imagePath=" + imagePath + ", uploadedAt=" + uploadedAt
				+ "]";
	}
    
}
