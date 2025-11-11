package com.coursemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseMaterialDTO {
    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private String materialType;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private Integer orderIndex;
    private LocalDateTime createdAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public CourseMaterialDTO(Long id, Long courseId, String title, String description, String materialType,
			String filePath, String fileName, Long fileSize, Integer orderIndex, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.title = title;
		this.description = description;
		this.materialType = materialType;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.orderIndex = orderIndex;
		this.createdAt = createdAt;
	}
	public CourseMaterialDTO() {
		super();
	}
    
    
}