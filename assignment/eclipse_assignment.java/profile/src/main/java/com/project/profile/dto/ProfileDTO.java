package com.project.profile.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ProfileDTO {
	@NotBlank(message = "Name cannot be empty")
	private String name;
	@NotBlank(message = "color cannot be empty")
	private String color;
	@NotNull(message = "Font size is required")
	private int fontSize;
	@NotBlank(message = "gender cannot be empty")
	private String gender;
	public ProfileDTO(String name, String color, int fontSize, String gender) {
		super();
		this.name = name;
		this.color = color;
		this.fontSize = fontSize;
		this.gender = gender;
	}
	public ProfileDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
