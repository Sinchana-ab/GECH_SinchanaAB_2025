package com.construction.companyManagement.dto;

import org.springframework.web.multipart.MultipartFile;

public class BlogDTO {
    private String title;
    private String content;
    private String author;
    private MultipartFile image;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

    // Getters and Setters
    
}

