package com.construction.companyManagement.model;


import jakarta.persistence.*;
@Entity
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String location;

    @Column(length = 1000)
    private String description;

    private String imagePath;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Project(Long id, String title, String location, String description, String imagePath) {
		super();
		this.id = id;
		this.title = title;
		this.location = location;
		this.description = description;
		this.imagePath = imagePath;
	}

	public Project() {
		super();
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", location=" + location + ", description=" + description
				+ ", imagePath=" + imagePath + "]";
	}
    
}

