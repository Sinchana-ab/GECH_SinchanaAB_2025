package com.construction.companyManagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    @Column(length = 1000)
    private String feedback;

    private int rating;

    private String imagePath;

    private LocalDate date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Testimonial(Long id, String clientName, String feedback, int rating, String imagePath, LocalDate date) {
		super();
		this.id = id;
		this.clientName = clientName;
		this.feedback = feedback;
		this.rating = rating;
		this.imagePath = imagePath;
		this.date = date;
	}

	public Testimonial() {
		super();
	}

	@Override
	public String toString() {
		return "Testimonial [id=" + id + ", clientName=" + clientName + ", feedback=" + feedback + ", rating=" + rating
				+ ", imagePath=" + imagePath + ", date=" + date + "]";
	}
    
}
