package com.construction.companyManagement.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(length = 3000)
    private String content;

    private String author;
    private String imagePath;
    private LocalDate publishedAt;
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public LocalDate getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(LocalDate publishedAt) {
		this.publishedAt = publishedAt;
	}
	public Blog(Long id, String title, String content, String author, String imagePath, LocalDate publishedAt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.imagePath = imagePath;
		this.publishedAt = publishedAt;
	}
	public Blog() {
		super();
	}
	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", author=" + author + ", imagePath="
				+ imagePath + ", publishedAt=" + publishedAt + "]";
	}
    
}
