package com.construction.companyManagement.dto;

import org.springframework.web.multipart.MultipartFile;

public class TestimonialDTO {
    private String clientName;
    private String feedback;
    private int rating;
    private MultipartFile image;
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

    // Getters and Setters
    
}

